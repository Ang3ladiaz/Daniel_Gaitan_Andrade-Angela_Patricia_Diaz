package com.digital.ClinicaOdontologica.controller;

import com.digital.ClinicaOdontologica.entity.Odontologo;
import com.digital.ClinicaOdontologica.entity.Paciente;
import com.digital.ClinicaOdontologica.exception.BadRequestException;
import com.digital.ClinicaOdontologica.exception.ResourceNotFoundException;
import com.digital.ClinicaOdontologica.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private PacienteService pacienteService;
    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }


    @PostMapping
    public ResponseEntity<Paciente> guardarPaciente(@RequestBody Paciente paciente) {
        return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
    }


    @GetMapping("/listar")
    public ResponseEntity<List<Paciente>> listarPacientes() throws BadRequestException {
        List<Paciente> pacientes = pacienteService.listarPacientes();
        if(pacientes.isEmpty()){
            throw new BadRequestException("La lista se encuentra vacia");
        }else{
            return ResponseEntity.ok(pacientes);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Long id)throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorID(id);
        if(pacienteBuscado.isPresent()){
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.ok("Se elimino correctamente el paciente con id: "+ id);
        }else {
            throw new ResourceNotFoundException("Error al eliminar el id: "+id+" no se encontró.");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPacientePorId(@PathVariable Long id)throws ResourceNotFoundException{
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPacientePorID(id);
        if(pacienteBuscado.isPresent()){
            return ResponseEntity.ok(pacienteBuscado.get());
        }else {
            throw new ResourceNotFoundException("Error al buscar id: "+id+" no se encontró.");
        }
    }


    @PutMapping
    public ResponseEntity<String> actualizarPaciente(@RequestBody Paciente paciente)throws BadRequestException{
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorID(paciente.getId());
        if(pacienteBuscado.isPresent()){
            pacienteService.actualizarPaciente(paciente);
            return ResponseEntity.ok("Se actualizo correctamente el paciente con id: "+paciente.getId()+" Nombre: "+paciente.getNombre());
        }else{
            throw new BadRequestException("El paciente con id: "+paciente.getId()+" no se encuentra.");

        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Paciente> buscarPacientePorEmail(@PathVariable String email)throws ResourceNotFoundException{
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPacientePorCorreo(email);
        if(pacienteBuscado.isPresent()){
            return ResponseEntity.ok(pacienteBuscado.get());
        }else {
            throw new ResourceNotFoundException("El email ingresado no se encuentra.");

        }
    }

    @GetMapping("nombre/{nombre}")
    public ResponseEntity<Paciente> buscarPacientePorNombre(@PathVariable String nombre)throws  ResourceNotFoundException{
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacienteXNombre(nombre);
        if(pacienteBuscado.isPresent()){
            return ResponseEntity.ok(pacienteBuscado.get());
        }else{
            throw new ResourceNotFoundException("Odontologo buscado por nombre es inexistente");

        }
    }


}
