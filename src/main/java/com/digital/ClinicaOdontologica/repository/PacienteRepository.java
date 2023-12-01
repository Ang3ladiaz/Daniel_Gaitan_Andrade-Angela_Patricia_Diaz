package com.digital.ClinicaOdontologica.repository;

import com.digital.ClinicaOdontologica.entity.Odontologo;
import com.digital.ClinicaOdontologica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long>{
    Optional<Paciente> findByEmail(String email);
    Optional<Paciente> findBynombreLike(String nombre);

}
