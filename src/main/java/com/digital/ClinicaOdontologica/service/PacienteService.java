package com.digital.ClinicaOdontologica.service;

import com.digital.ClinicaOdontologica.entity.Odontologo;
import com.digital.ClinicaOdontologica.entity.Paciente;
import com.digital.ClinicaOdontologica.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
   @Autowired//relacion de asosiacion
    private PacienteRepository pacienteRepository;

    public Paciente guardarPaciente(Paciente paciente){
        return pacienteRepository.save(paciente);
    }
    public Optional<Paciente> buscarPacientePorID(Long id){
        return pacienteRepository.findById(id);
    }// optional trabaja solo la excepcion porque si no esta el paciente devuelve un null directamente
    public void eliminarPaciente(Long id){
        pacienteRepository.deleteById(id);
    }
    public void actualizarPaciente(Paciente paciente){
        pacienteRepository.save(paciente);
    }
    public List<Paciente> listarPacientes(){
       return pacienteRepository.findAll();
    }
    public Optional<Paciente> buscarPacientePorCorreo(String email){
        return  pacienteRepository.findByEmail(email);
    }
    public Optional<Paciente> buscarPacienteXNombre(String palabra){
        return pacienteRepository.findBynombreLike(palabra);
    }

}
