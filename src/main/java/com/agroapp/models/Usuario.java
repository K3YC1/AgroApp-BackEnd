package com.agroapp.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_usuario;
    
    private String nombreUsuario;
    
    private String correo;
    
    private String contraseña;
    
    private LocalDateTime creado_en;
    
    private LocalDateTime actualizado_en;
    
    private String codigoVerificacion;
    
    private LocalDateTime expiracionCodigo;

	private LocalDateTime expiracionCodigoRecuperacion;

	private boolean confirmado;

	private String imagen_perfil;

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

	public LocalDateTime getCreado_en() {
		return creado_en;
	}

	public void setCreado_en(LocalDateTime localDateTime) {
		this.creado_en = localDateTime;
	}

	public LocalDateTime getActualizado_en() {
		return actualizado_en;
	}

	public void setActualizado_en(LocalDateTime actualizado_en) {
		this.actualizado_en = actualizado_en;
	}

	public String getCodigoVerificacion() {
		return codigoVerificacion;
	}

	public void setCodigoVerificacion(String codigoVerificacion) {
		this.codigoVerificacion = codigoVerificacion;
	}

	public LocalDateTime getExpiracionCodigo() {
		return expiracionCodigo;
	}

	public void setExpiracionCodigo(LocalDateTime expiracionCodigo) {
		this.expiracionCodigo = expiracionCodigo;
	}

	public LocalDateTime getExpiracionCodigoRecuperacion() {
		return expiracionCodigoRecuperacion;
	}

	public void setExpiracionCodigoRecuperacion(LocalDateTime expiracionCodigoRecuperacion) {
		this.expiracionCodigoRecuperacion = expiracionCodigoRecuperacion;
	}

	public boolean isConfirmado() {
		return confirmado;
	}

	public void setConfirmado(boolean confirmado) {
		this.confirmado = confirmado;
	}

	public String getImagen_perfil() {
		return imagen_perfil;
	}

	public void setImagen_perfil(String imagen_perfil) {
		this.imagen_perfil = imagen_perfil;
	}
    
}

