package com.digital.ClinicaOdontologica.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="pacientes")
@Getter
@Setter
public class Paciente {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String apellido;
    @Column
    private String nombre;
    @Column(unique = true)
    private String email;
    @Column(unique = true, nullable = false)
    private String dni;
    @Column
    private LocalDate fechaIngreso;

    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="domicilio_id", referencedColumnName = "id")
    private Domicilio domicilio;

    @OneToMany(mappedBy ="paciente",fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Turno> turnos= new HashSet<>();

    @Override
    public String toString() {
        return "\nPaciente " +id+
                "\n\tApellido: " + apellido +
                "\n\tNombre:" + nombre +
                "\n\temail:" + email +
                "\n\tdni:" + dni +
                "\n\tFecha de ingreso:" + fechaIngreso +
                "\n----------------------------------------------------------------";
    }

}
