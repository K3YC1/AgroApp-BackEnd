package com.agroapp.controllers;

import java.util.List;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import com.agroapp.models.Login;
import com.agroapp.models.Usuario;
import com.agroapp.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class LoginController {

	@Autowired
    UsuarioService usuarioService;

    @Autowired
    HttpSession httpSession;

    @PostMapping("/usuario/login")
    public ResponseEntity authenticateUser(@RequestBody Login login){

        // Get User Email:
        List<String> usuarioCorreo = usuarioService.checkUserEmail(login.getCorreo());

        // Check If Email Is Empty:
        if(usuarioCorreo.isEmpty() || usuarioCorreo == null){
            return new ResponseEntity("Porfavor complete el correo para acceder", HttpStatus.NOT_FOUND);
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
        
	     // Check if the user is confirmed before allowing login
	        if (!usuario.isConfirmado()) {
	            return new ResponseEntity("El usuario no ha confirmado su cuenta, porfavor revise su codigo de confirmacion en su correo e ingreselo para confirmar", HttpStatus.FORBIDDEN);
	        }

        // Establecer una variable de sesión para indicar que el usuario está autenticado
        httpSession.setAttribute("usuarioAutenticado", true);
        
        return new ResponseEntity(usuario, HttpStatus.OK);
    }

    @GetMapping("/usuario/logout")
    public ResponseEntity logoutUser() {
        // Invalidar la sesión
        httpSession.invalidate();
        return new ResponseEntity("Sesión cerrada exitosamente", HttpStatus.OK);
    }
}
