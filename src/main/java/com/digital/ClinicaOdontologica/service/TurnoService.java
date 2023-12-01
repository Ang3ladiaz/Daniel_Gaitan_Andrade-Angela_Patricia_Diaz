package com.digital.ClinicaOdontologica.service;
import com.digital.ClinicaOdontologica.dto.TurnoDto;
import com.digital.ClinicaOdontologica.entity.Odontologo;
import com.digital.ClinicaOdontologica.entity.Paciente;
import com.digital.ClinicaOdontologica.entity.Turno;
import com.digital.ClinicaOdontologica.exception.ResourceNotFoundException;
import com.digital.ClinicaOdontologica.repository.OdontologoRepository;
import com.digital.ClinicaOdontologica.repository.PacienteRepository;
import com.digital.ClinicaOdontologica.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    @Autowired
   private TurnoRepository turnoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private OdontologoRepository odontologoRepository;

    // hacer el mapper de manera manual

    public TurnoDto guardarTurno(Turno turno) {
        Turno turnoGuardado=turnoRepository.save(turno);
        return turnoAturnoDto(turnoGuardado);
    }
    public List<TurnoDto> listarTurnos(){
        List<Turno> turnosEncontrados= turnoRepository.findAll();
        //se necesita recorrer la lista para transformarlos de un lugar al otro.
        List<TurnoDto> turnosDtoLista= new ArrayList<>();
        for (Turno turno:turnosEncontrados) {
            turnosDtoLista.add(turnoAturnoDto(turno));
        }
        return turnosDtoLista;
    }
    public Optional<TurnoDto> buscarTurnoPorID(Long id){
        Optional<Turno> turnoBuscado= turnoRepository.findById(id);
        if(turnoBuscado.isPresent()){
            return Optional.of(turnoAturnoDto(turnoBuscado.get()));
        }else {
            return Optional.empty();
        }
    }

    public void eliminarTurno(Long id){
      turnoRepository.deleteById(id);
    }
    public void actualizarTurno(Turno turno){
      //Turno turnoGuardado=turnoRepository.save(turno);
      //turnoAturnoDto(turnoGuardado);
        turnoRepository.save(turno);
    }

    public TurnoDto turnoAturnoDto(Turno turno){
        TurnoDto turnoDto=new TurnoDto();
        Optional<Paciente> paciente = pacienteRepository.findById(turno.getPaciente().getId());
        Optional<Odontologo> odontologo = odontologoRepository.findById(turno.getOdontologo().getId());
        //necesitamos cargar la informacion
        turnoDto.setId(turno.getId());

        if (paciente.isPresent() && odontologo.isPresent()){
            turnoDto.setPacienteId(paciente.get().getId());
            turnoDto.setPacienteNombre(paciente.get().getNombre());
            turnoDto.setOdontologoId(odontologo.get().getId());
            turnoDto.setOdontologoNombre(odontologo.get().getNombre());
        }

        turnoDto.setFecha(turno.getFecha());
        //devolvemos
        return turnoDto;
    }
    public Turno turnoDtoAturno(TurnoDto turnoDto){
        Turno turno= new Turno();
        //necesitamos cargar los datos
        Odontologo odontologo =new Odontologo();
        Paciente paciente=new Paciente();
        turno.setId(turnoDto.getId());
        turno.setFecha(turnoDto.getFecha());
        turnoDto.setPacienteNombre(turno.getPaciente().getNombre());
        turnoDto.setOdontologoNombre(turno.getOdontologo().getNombre());
        //devolvemos
        return turno;
    }

}
