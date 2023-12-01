package com.digital.ClinicaOdontologica.controller;
import com.digital.ClinicaOdontologica.dto.TurnoDto;
import com.digital.ClinicaOdontologica.entity.Odontologo;
import com.digital.ClinicaOdontologica.entity.Paciente;
import com.digital.ClinicaOdontologica.entity.Turno;
import com.digital.ClinicaOdontologica.exception.BadRequestException;
import com.digital.ClinicaOdontologica.exception.ResourceNotFoundException;
import com.digital.ClinicaOdontologica.service.OdontologoService;
import com.digital.ClinicaOdontologica.service.PacienteService;
import com.digital.ClinicaOdontologica.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private TurnoService turnoService;
    private OdontologoService odontologoService;
    private PacienteService pacienteService;

    @Autowired
    public TurnoController(TurnoService turnoService, OdontologoService odontologoService, PacienteService pacienteService) {
        this.turnoService = turnoService;
        this.odontologoService = odontologoService;
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<TurnoDto> registrarTurno(@RequestBody Turno turno) throws BadRequestException {



        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorID(turno.getPaciente().getId());

        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoPorId(turno.getOdontologo().getId());

        if (pacienteBuscado.isPresent() && odontologoBuscado.isPresent()) {
            return ResponseEntity.ok(turnoService.guardarTurno(turno));
        } else {
            throw new BadRequestException("El paciente o el odontologo no existen");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTurno (@PathVariable Long id) throws ResourceNotFoundException {
        Optional<TurnoDto> turnoBuscado =turnoService.buscarTurnoPorID(id);
       if (turnoBuscado.isPresent()){
           turnoService.eliminarTurno(id);
            return ResponseEntity.ok("Se elimino correctamente el id: "+id);

        }else{
           throw new ResourceNotFoundException("Error al eliminar el turno con id: "+id);
        }

    }

   @PutMapping
    public ResponseEntity<String> actualizarTurno (@RequestBody Turno turno)throws BadRequestException{
      Optional<TurnoDto> turnoBuscado= turnoService.buscarTurnoPorID(turno.getId());
      if(turnoBuscado.isPresent()){
          if(odontologoService.buscarOdontologoPorId(turno.getOdontologo().getId()).isPresent() && pacienteService.buscarPacientePorID(turno.getPaciente().getId()).isPresent()){
            turnoService.actualizarTurno(turno);
               return ResponseEntity.ok("Se ha actualizado correctamente el turno "+ turno.getId());
        }else{
              throw new BadRequestException("No se ha actualizo porque paciente u odontologo no se encuentran");
           }
       }else{
            throw new BadRequestException("No se actuliza, no se encuentra el turno");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoDto> buscarTurnoPorId (@PathVariable Long id)throws ResourceNotFoundException{
       Optional <TurnoDto> turnoBuscado= turnoService.buscarTurnoPorID(id);
        if(turnoBuscado.isPresent()){
            return ResponseEntity.ok(turnoBuscado.get());
        }else{
            throw new ResourceNotFoundException("Turno con id: "+id+" no se encuetra en la basa de datos");
        }
    }
    @GetMapping("/listar")
    public ResponseEntity<List<TurnoDto>> traerTodosLosTurnos() {
        return ResponseEntity.ok(turnoService.listarTurnos());
    }

}
