package com.digital.ClinicaOdontologica.controller;
import com.digital.ClinicaOdontologica.entity.Odontologo;
import com.digital.ClinicaOdontologica.exception.BadRequestException;
import com.digital.ClinicaOdontologica.exception.ResourceNotFoundException;
import com.digital.ClinicaOdontologica.service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {
    @Autowired
    private OdontologoService odontologoService;


    @PostMapping
    public ResponseEntity<Odontologo> guardarOdontologo(@RequestBody Odontologo odontologo) {
        Odontologo odontologoCreado= odontologoService.guardarOdontologo(odontologo);
        return  ResponseEntity.ok(odontologoCreado);
    }


    @GetMapping("/listar")
    public ResponseEntity <List<Odontologo>> listarOdontologos()throws BadRequestException {
        List<Odontologo> odontologos = odontologoService.listarOdontologos();
        if(odontologos.isEmpty()){
            throw new BadRequestException("No hay datos para listar odontologos");

        }else{
            return ResponseEntity.ok(odontologos);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarOdontologoPorID (@PathVariable Long id)throws ResourceNotFoundException{
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoPorId(id);
        if(odontologoBuscado.isPresent()){
            return ResponseEntity.ok(odontologoBuscado.get());
        }else{
            throw new ResourceNotFoundException("No se encuentra el odontologo con : "+id+" en la base de datos");

        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Long id)throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoPorId(id);
        if(odontologoBuscado.isPresent()){
            odontologoService.eliminarOdontologo(id);
            return ResponseEntity.ok("El Odontologo con id: "+ id+" fue eliminado correctamente");
        }else {
            throw new ResourceNotFoundException("El odontologo con id: "+id+" no se encuentra.");

        }
    }


    @PutMapping
    public ResponseEntity<String> actualizarOdontologo(@RequestBody Odontologo odontologo)throws BadRequestException{
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoPorId(odontologo.getId());
        if(odontologoBuscado.isPresent()){
            odontologoService.actualizarOdontologo(odontologo);
            return ResponseEntity.ok("El odontologo con id: "+ odontologo.getId()+ " fue actualizado correctamente.");
        }else {
            throw new BadRequestException("El odontologo con id : "+odontologo.getId()+" no se encuentra.");


        }
    }


    @GetMapping("nombre/{nombre}")
    public ResponseEntity<Odontologo> buscarOdontologoPorNombre(@PathVariable String nombre)throws  ResourceNotFoundException{
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoXNombre(nombre);
        if(odontologoBuscado.isPresent()){
            return ResponseEntity.ok(odontologoBuscado.get());
        }else{
            throw new ResourceNotFoundException("Odontologo buscado por nombre es inexistente");

        }
    }
}
