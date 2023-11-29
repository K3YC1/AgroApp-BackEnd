package com.agroapp.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    private int id_usuario;
    
    private String nombreUsuario;
    
    private String correo;
    
    private String contraseña;
    
    private Date creado_en;
    
    @Transient
    private Date actualizado_en;

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public Date getCreado_en() {
		return creado_en;
	}

	public void setCreado_en(Date creado_en) {
		this.creado_en = creado_en;
	}

	public Date getActualizado_en() {
		return actualizado_en;
	}

	public void setActualizado_en(Date actualizado_en) {
		this.actualizado_en = actualizado_en;
	}
}

