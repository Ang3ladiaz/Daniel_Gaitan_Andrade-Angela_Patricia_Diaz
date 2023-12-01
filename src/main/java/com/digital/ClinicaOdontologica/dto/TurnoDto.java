package com.digital.ClinicaOdontologica.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class TurnoDto {
    private Long id;
    private Long pacienteId;
    private Long odontologoId;
    private String pacienteNombre;
    private String odontologoNombre;
    private LocalDate fecha;

    public TurnoDto() {
    }

    public TurnoDto(Long id, String pacienteNombre, String odontologoNombre, LocalDate fecha) {
        this.id = id;
        this.pacienteNombre = pacienteNombre;
        this.odontologoNombre = odontologoNombre;
        this.fecha = fecha;
    }
}
