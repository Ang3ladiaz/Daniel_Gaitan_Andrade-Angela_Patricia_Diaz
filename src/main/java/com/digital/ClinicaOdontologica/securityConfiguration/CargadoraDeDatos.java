package com.digital.ClinicaOdontologica.securityConfiguration;

import com.digital.ClinicaOdontologica.entity.security.AppUsuario;
import com.digital.ClinicaOdontologica.entity.security.AppUsuarioRol;
import com.digital.ClinicaOdontologica.service.security.AppUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CargadoraDeDatos implements ApplicationRunner {


    AppUsuarioService appUsuarioService;

    @Autowired
    public CargadoraDeDatos(AppUsuarioService appUsuarioService) {
        this.appUsuarioService = appUsuarioService;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
        //ROL ADMINISTRADOR
        String passAdmin="pass";
        String passCifradoAdmin= passwordEncoder.encode(passAdmin);
        System.out.println("La contraseña cifrada es: "+passCifradoAdmin);
        appUsuarioService.guardarUsuario(new AppUsuario("Juan","Gutierrez","juang@gmail.com",passCifradoAdmin, AppUsuarioRol.ROLE_ADMIN));
        //ROL USUARIO
        String passUser="pass";
        String passCifradoUser= passwordEncoder.encode(passUser);
        System.out.println("La contraseña cifrada es: "+passCifradoUser);
        appUsuarioService.guardarUsuario(new AppUsuario("Andres","Bermudez","anber@gmail.com",passCifradoUser, AppUsuarioRol.ROLE_USER));
    }
}
