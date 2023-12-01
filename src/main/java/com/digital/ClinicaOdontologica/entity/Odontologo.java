package com.digital.ClinicaOdontologica.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="odontologos")
@Getter
@Setter
public class Odontologo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column (unique = true,nullable = false)
    private String matricula;
    @Column
    private String nombre;
    @Column
    private String apellido;

    @OneToMany(mappedBy = "odontologo",fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Turno> turnos= new HashSet<>();

    public Odontologo(String matricula, String nombre, String apellido) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Odontologo() {
    }

    @Override
    public String toString() {
        return "\nOdontologo " +id+
                "\n\tMatricula: " + matricula +
                "\n\tNombre:" + nombre +
                "\n\tApellido:" + apellido +
                "\n----------------------------------------------------------------";
    }


}
