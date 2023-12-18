package com.agroapp;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.agroapp.services.UsuarioService;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class AgroappApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(AgroappApplication.class, args);
		// Invocar el método para eliminar usuarios no confirmados expirados
        applicationContext.getBean(UsuarioService.class).eliminarUsuariosNoConfirmadosExpirados();
	}

}
