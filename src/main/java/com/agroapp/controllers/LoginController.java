package com.agroapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agroapp.models.Login;
import com.agroapp.models.Usuario;
import com.agroapp.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class LoginController {

	@Autowired
    UsuarioService usuarioService;

    @PostMapping("/usuario/login")
    public ResponseEntity authenticateUser(@RequestBody Login login){

        // Get User Email:
        List<String> usuarioCorreo = usuarioService.checkUserEmail(login.getCorreo());

        // Check If Email Is Empty:
        if(usuarioCorreo.isEmpty() || usuarioCorreo == null){
            return new ResponseEntity("El correo no existe", HttpStatus.NOT_FOUND);
        }
        // End Of Check If Email Is Empty.

        // Get Hashed User Password:
        String hashed_password = usuarioService.checkUserPasswordByEmail(login.getCorreo());

        // Validate User Password:
        if(!BCrypt.checkpw(login.getContraseña(), hashed_password)){
            return new ResponseEntity("Correo o contraseña incorrectas", HttpStatus.BAD_REQUEST);
        }

        // Set User Object:
        Usuario usuario = usuarioService.getUserDetailsByEmail(login.getCorreo());
        return new ResponseEntity(usuario, HttpStatus.OK);
    }
}
