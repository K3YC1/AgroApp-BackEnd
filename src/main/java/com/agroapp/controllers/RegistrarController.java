package com.agroapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agroapp.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class RegistrarController {

	@Autowired
    UsuarioService usuarioService;


    @PostMapping("/usuario/registrar")
    public ResponseEntity registerNewUser(@RequestParam("nombreUsuario")String nombreUsuario,
                                          @RequestParam("correo")String correo,
                                          @RequestParam("contraseña")String contraseña){

        if(nombreUsuario.isEmpty() || correo.isEmpty() || contraseña.isEmpty()){
            return new ResponseEntity<>("Porfavor complete los datos!", HttpStatus.BAD_REQUEST);
        }
        
     // Check if the email is already registered
        if (usuarioService.isUserAlreadyRegistered(correo)) {
            return new ResponseEntity<>("El correo ya está registrado. Por favor, use otro correo.", HttpStatus.BAD_REQUEST);
        }

        // Encrypt / Hash  Password:
        String hashed_password = BCrypt.hashpw(contraseña, BCrypt.gensalt());

        // Register New User:
        int result = usuarioService.registerNewUserServiceMethod(nombreUsuario, correo, hashed_password);

        if(result != 1){
            return new ResponseEntity<>("El registro de Usuario Fallo!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("El registro de Usuario fue Exitoso!", HttpStatus.OK);

    }
}
