package com.agroapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
	
	@GetMapping("/prueba")
    public String testEndpoint(){
        return "El API REST se inicio correctamente";
    }
}
