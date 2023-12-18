package com.agroapp.controllers;

import com.agroapp.models.Usuario;
import com.agroapp.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;
	
	@GetMapping("/prueba")
    public String testEndpoint(){
        return "El API REST se inicio correctamente";
    }

    @PostMapping("/usuario/recuperar-contraseña")
    public ResponseEntity<String> recuperarContraseña(@RequestParam String correo) {
        Usuario usuario = usuarioService.getUserDetailsByEmail(correo);

        if (usuario != null) {
            // Generar código de recuperación y enviar correo
            usuarioService.enviarCodigoRecuperacionContraseña(correo);
            return ResponseEntity.ok("Se ha enviado un correo con el código de recuperación.");
        } else {
            return ResponseEntity.badRequest().body("No se encontró un usuario con ese correo.");
        }
    }

    @PostMapping("/usuario/confirmar-cambio-contraseña")
    public ResponseEntity<String> confirmarCambioContraseña(
            @RequestParam String codigoln,
            @RequestParam String nuevaContraseña
    ) {
        if (usuarioService.confirmarCambioContraseña(codigoln, nuevaContraseña)) {
            return ResponseEntity.ok("La contraseña se ha cambiado exitosamente.");
        } else {
            return ResponseEntity.badRequest().body("No se pudo confirmar el cambio de contraseña. " +
                    "Verifica que el código sea válido y no haya expirado.");
        }
    }
}
