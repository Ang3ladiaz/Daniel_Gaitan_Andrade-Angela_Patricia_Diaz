package com.digital.ClinicaOdontologica.service;
;
import com.digital.ClinicaOdontologica.entity.Odontologo;
import com.digital.ClinicaOdontologica.repository.OdontologoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {
    private static final Logger LOGGER = Logger.getLogger(OdontologoService.class);
    @Autowired
    private OdontologoRepository odontologoRepository;
    @Autowired
    private ObjectMapper mapper;

    // como suluciono esto!!
    public Odontologo guardarOdontologo(Odontologo odontologo) {
        return  odontologoRepository.save(odontologo);
    }

    public Optional<Odontologo> buscarOdontologoPorId (Long id){
        return odontologoRepository.findById(id);
    }
    public List<Odontologo> listarOdontologos(){
        return odontologoRepository.findAll();
    }
    public Optional<Odontologo> buscarOdontologoXNombre(String palabra){
        return odontologoRepository.findBynombreLike(palabra);
    }
    public void eliminarOdontologo(Long id){
        Optional<Odontologo> odontologoEliminar =buscarOdontologoPorId(id);
        if(!odontologoEliminar.filter(odontologo -> odontologo.getId() != null && odontologo.getId() > 1).isPresent()){
            LOGGER.error("debe ingresar un id valido");
            return;
        }
        LOGGER.info("Se elimino Odontologo correctamente");
        odontologoRepository.deleteById(id);
    }
    public void actualizarOdontologo(Odontologo odontologo){
        odontologoRepository.save(odontologo);
    }}


