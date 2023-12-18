package com.agroapp.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.agroapp.models.Usuario;

import jakarta.transaction.Transactional;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
	
	@Query(value = "SELECT correo FROM usuarios WHERE correo = :correo ", nativeQuery = true)
    List<String> checkUserEmail(@Param("correo") String correo);

    @Query(value = "SELECT contraseña FROM usuarios WHERE correo = :correo", nativeQuery = true)
    String checkUserPasswordByEmail(@Param("correo") String correo);

    @Query(value = " SELECT * FROM usuarios WHERE correo = :correo", nativeQuery = true)
    Usuario GetUserDetailsByEmail(@Param("correo") String correo);



    @Transactional
    @Modifying
    @Query(value = "INSERT INTO usuarios(nombreUsuario, correo, contraseña) VALUES(:nombreUsuario, :correo, :contraseña)", nativeQuery = true)
    int registerNewUser(@Param("nombreUsuario") String nombreUsuario,
                        @Param("correo") String correo,
                        @Param("contraseña") String contraseña);
    
    
    Optional<Usuario> findByCodigoVerificacion(String codigoVerificacion);
    
    @Query("SELECT u FROM Usuario u WHERE u.confirmado = false AND u.expiracionCodigo < :fechaActual")
    List<Usuario> findByConfirmadoFalseAndExpiracionCodigoBefore(@Param("fechaActual") LocalDateTime fechaActual);

    Optional<Usuario> findByCorreo(String correo);
}
