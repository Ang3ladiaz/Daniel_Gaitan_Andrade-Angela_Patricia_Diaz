package com.digital.ClinicaOdontologica.controller;
import com.digital.ClinicaOdontologica.entity.Odontologo;
import com.digital.ClinicaOdontologica.exception.BadRequestException;
import com.digital.ClinicaOdontologica.exception.ResourceNotFoundException;
import com.digital.ClinicaOdontologica.service.OdontologoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// Importar las clases necesarias

public class OdontologoControllerTest {

    @Mock
    private OdontologoService odontologoService;

    @InjectMocks
    private OdontologoController odontologoController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void guardarOdontologoTest() {
        Odontologo odontologo = new Odontologo();
        odontologo.setNombre("Juan");
        odontologo.setApellido("Perez");
        odontologo.setMatricula("JP123");
        when(odontologoService.guardarOdontologo(odontologo)).thenReturn(odontologo);
        ResponseEntity<Odontologo> response = odontologoController.guardarOdontologo(odontologo);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(odontologo, response.getBody());
    }
    @Test
    public void buscarOdontologoPorId() throws ResourceNotFoundException {
        Odontologo odontologo= new Odontologo("5687","Juliana","Rangel");
        Long id = 1L;
        when(odontologoService.buscarOdontologoPorId(id)).thenReturn(Optional.of(odontologo));
        ResponseEntity<Odontologo> response = odontologoController.buscarOdontologoPorID(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(odontologo,response.getBody());
    }
    @Test
    public void buscarOdontologoPorIdNoEncontrado() throws ResourceNotFoundException {
        Long id= 1L;
        when(odontologoService.buscarOdontologoPorId(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,() ->odontologoController.buscarOdontologoPorID(id));
    }
    @Test
    public void buscarOdontologoPorNombre() throws ResourceNotFoundException {
        Odontologo odontologo= new Odontologo("3256","Maria","Morales");
        String nombre= "Maria";
        when(odontologoService.buscarOdontologoXNombre(nombre)).thenReturn(Optional.of(odontologo));
        ResponseEntity<Odontologo> response = odontologoController.buscarOdontologoPorNombre(nombre);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(odontologo,response.getBody());
    }
    @Test
    public void buscarOdontologoPorNombreNoEncontrado() throws ResourceNotFoundException {
        String nombre= "Maria";
        when(odontologoService.buscarOdontologoXNombre(nombre)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,() ->odontologoController.buscarOdontologoPorNombre(nombre));
    }
    @Test
    public void actualizarOdontologoTest() throws BadRequestException {
        Odontologo odontologo= new Odontologo("3256","Maria","Morales");
        odontologo.setId(1L);
        when(odontologoService.buscarOdontologoPorId(odontologo.getId())).thenReturn(Optional.of(odontologo));
        ResponseEntity<String> res= odontologoController.actualizarOdontologo(odontologo);
        assertEquals(HttpStatus.OK,res.getStatusCode());
        assertEquals("El odontologo con id: "+odontologo.getId()+" fue actualizado correctamente.", res.getBody());
        verify(odontologoService).actualizarOdontologo(odontologo);
    }
    @Test
    public void actualizarOdontologoInexistente() throws ResourceNotFoundException {
        Odontologo odontologo= new Odontologo("3256","Maria","Morales");
        odontologo.setId(1L);
        when(odontologoService.buscarOdontologoPorId(odontologo.getId())).thenReturn(Optional.empty());
        assertThrows(BadRequestException.class,() -> odontologoController.actualizarOdontologo(odontologo));
        verify(odontologoService, Mockito.never()).actualizarOdontologo(odontologo);
    }

    @Test
    public void eliminarOdontoloTest() throws ResourceNotFoundException {
        Long id= 1L;
        when(odontologoService.buscarOdontologoPorId(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,() -> odontologoController.eliminarOdontologo(id));
    }


    @Test
    public void listarOdontologosTest() throws BadRequestException {
        List<Odontologo> odontologos = new ArrayList<>();
        odontologos.add(new Odontologo("MY8965","Mario","Roa"));
        odontologos.add(new Odontologo("MC2563","Cesar","Riaño"));
        when(odontologoService.listarOdontologos()).thenReturn(odontologos);
        ResponseEntity<List<Odontologo>> response =odontologoController.listarOdontologos();
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(odontologos,response.getBody());
    }

    @Test
    public void listarOdontologosVacioTest(){
        List<Odontologo> odontologos = new ArrayList<>();
        when(odontologoService.listarOdontologos()).thenReturn(odontologos);
        assertThrows(BadRequestException.class,() -> odontologoController.listarOdontologos());
    }
}


