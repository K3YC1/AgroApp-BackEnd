package com.agroapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agroapp.models.Usuario;
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
            return new ResponseEntity<>("Porfavor complete los datos!", HttpStatus.NOT_FOUND);
        }
        
     // Verificar que el correo tenga "@gmail.com" al final
        if (!correo.toLowerCase().endsWith("@gmail.com")) {
            return new ResponseEntity<>("Porfavor introduzca un correo valido!", HttpStatus.BAD_REQUEST);
        }
        
     // Check if the email is already registered
        if (usuarioService.isUserAlreadyRegistered(correo)) {
            return new ResponseEntity<>("El correo ya está registrado. Por favor, use otro correo.", HttpStatus.FORBIDDEN);
        }

        // Encrypt / Hash  Password:
        String hashed_password = BCrypt.hashpw(contraseña, BCrypt.gensalt());

     // Crear el usuario sin confirmar y enviar el código de verificación
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombreUsuario(nombreUsuario);
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setContraseña(hashed_password);

        usuarioService.enviarCodigoVerificacion(nuevoUsuario);

        return new ResponseEntity<>("Se ha enviado un código de verificación. Por favor, ingrese el código para confirmar su registro.",
                HttpStatus.OK);
    }
    
    @PostMapping("/usuario/confirmar")
    public ResponseEntity confirmUser(@RequestParam("codigo") String codigo) {
        if (usuarioService.confirmarUsuarioConCodigo(codigo)) {
            return new ResponseEntity<>("¡Registro confirmado con éxito!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("El código de verificación no es válido o ha expirado.", HttpStatus.BAD_REQUEST);
        }
    }
}
