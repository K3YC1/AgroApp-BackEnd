package com.agroapp.services;

import java.time.LocalDateTime;

import com.agroapp.exception.UsuarioNoEncontradoException;
import jakarta.transaction.Transactional;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.agroapp.models.Usuario;
import com.agroapp.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	@Autowired
    UsuarioRepository usuarioRepository;

    public int registerNewUserServiceMethod(String nombreUsuario, String correo, String contraseña){
        return usuarioRepository.registerNewUser(nombreUsuario,correo,contraseña);
    }
    public List<String> checkUserEmail(String correo){
        return usuarioRepository.checkUserEmail(correo);
    }
    // End Of Check User Email Services Method.

    public String checkUserPasswordByEmail(String correo){
        return usuarioRepository.checkUserPasswordByEmail(correo);
    }
    // End Of Check User Password Services Method.

    public Usuario getUserDetailsByEmail(String correo){
        return usuarioRepository.GetUserDetailsByEmail(correo);
    }
    // End Of Get User Details By Email.
    
    public boolean isUserAlreadyRegistered(String correo) {
        List<String> existingUsers = usuarioRepository.checkUserEmail(correo);
        return !existingUsers.isEmpty();
    }
    
    public void enviarCodigoVerificacion(Usuario usuario) {
    	String codigoVerificacion = generarCodigoVerificacion();
        usuario.setCodigoVerificacion(codigoVerificacion);
        usuario.setExpiracionCodigo(LocalDateTime.now().plusHours(1));

        // Almacena al usuario en la base de datos sin confirmar
        usuarioRepository.save(usuario);

        // Enviar el correo con el código de verificación
        enviarCorreoCodigoVerificacion(usuario.getCorreo(), codigoVerificacion);
    }

    private String generarCodigoVerificacion() {
        // Genera un código de 5 dígitos al azar
        Random random = new Random();
        int codigo = 10000 + random.nextInt(90000);
        return String.valueOf(codigo);
    }
    
    private void enviarCorreoCodigoVerificacion(String correo, String codigo) {
    	try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(correo);
            message.setSubject("Código de Verificación Agro Emy");
            message.setText("Su código de verificación es: " + codigo);

            javaMailSender.send(message);
        } catch (Exception e) {
            // Maneja errores de envío de correo electrónico según tus necesidades
            e.printStackTrace();
        }
    }
    
    public boolean confirmarUsuarioConCodigo(String codigo) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByCodigoVerificacion(codigo);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();

            System.out.println("Fecha de expiración antes de confirmar: " + usuario.getExpiracionCodigo());

            if (!usuario.isConfirmado() && usuario.getExpiracionCodigo().isAfter(LocalDateTime.now())) {
            	// Agregar registros de depuración
                System.out.println("Antes de confirmar - creado_en: " + usuario.getCreado_en());
            	
            	// Actualiza el estado del usuario a confirmado y guarda en la base de datos
                usuario.setConfirmado(true);
                
                // Guardar la fecha y hora en la columna creado_en solo si se confirma
                usuario.setCreado_en(LocalDateTime.now());
                
                // Registros de depuración
                System.out.println("Después de confirmar - creado_en: " + usuario.getCreado_en());
                usuario.setCodigoVerificacion(null);

                // Establecer ExpiracionCodigo a un valor en el pasado
                usuario.setExpiracionCodigo(LocalDateTime.now().minusHours(1));

                // Registros de depuración
                System.out.println("Fecha de expiración después de confirmar: " + usuario.getExpiracionCodigo());

                // Guarda en la base de datos
                usuarioRepository.save(usuario);

                return true;
            }
        }
        return false;
    }
    
    public void eliminarUsuariosNoConfirmadosExpirados() {
        // Obtener la lista de usuarios no confirmados con código expirado
        List<Usuario> usuariosNoConfirmadosExpirados = usuarioRepository
                .findByConfirmadoFalseAndExpiracionCodigoBefore(LocalDateTime.now());

        // Eliminar los usuarios no confirmados con código expirado
        usuarioRepository.deleteAll(usuariosNoConfirmadosExpirados);
    }

    private String generarCodigoRecuperacion() {
        // Genera un código alfanumérico de longitud 8
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder codigoln = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            codigoln.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }

        return codigoln.toString();
    }

    private void enviarCorreoCodigoRecuperacion(String correo, String codigoln) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(correo);
            message.setSubject("Código de Recuperación de Contraseña Agro Emy");
            message.setText("Su código de recuperación de contraseña es: " + codigoln);

            javaMailSender.send(message);
        } catch (Exception e) {
            // Maneja errores de envío de correo electrónico según tus necesidades
            e.printStackTrace();
        }
    }

    public boolean confirmarCambioContraseña(String codigoln, String nuevaContraseña) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByCodigoVerificacion(codigoln);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();

            // Log de depuración
            System.out.println("Código ingresado: " + codigoln);
            System.out.println("Fecha y hora actual: " + LocalDateTime.now());
            System.out.println("Fecha de expiración del código de recuperación: " + usuario.getExpiracionCodigoRecuperacion());

            // Log de depuración adicional
            System.out.println("Estado de confirmación del usuario: " + usuario.isConfirmado());
            // Verificar que el código de verificación de recuperación sea válido y no haya expirado
            if (usuario.isConfirmado() && usuario.getExpiracionCodigoRecuperacion().isAfter(LocalDateTime.now())) {

                // Encrypt / Hash de la nueva contraseña
                String contraseñaEncriptada = BCrypt.hashpw(nuevaContraseña, BCrypt.gensalt());
                // Actualizar la contraseña y limpiar el código de verificación de recuperación
                usuario.setContraseña(contraseñaEncriptada);

                // Actualizar la fecha de actualización de la contraseña
                usuario.setActualizado_en(LocalDateTime.now());

                usuario.setCodigoVerificacion(null);
                usuario.setExpiracionCodigoRecuperacion(null);

                // Guardar en la base de datos
                usuarioRepository.save(usuario);

                // Log de depuración
                System.out.println("Contraseña actualizada exitosamente.");
                System.out.println("Fecha de actualización de la contraseña: " + usuario.getActualizado_en());

                return true;
            }
        }

        // Log de depuración
        System.out.println("No se pudo confirmar el cambio de contraseña. Condición de expiración: " +
                LocalDateTime.now().isBefore(optionalUsuario.map(Usuario::getExpiracionCodigoRecuperacion).orElse(null)));

        return false;
    }

    public void enviarCodigoRecuperacionContraseña(String correo) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByCorreo(correo);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            String codigoRecuperacion = generarCodigoRecuperacion();
            usuario.setCodigoVerificacion(codigoRecuperacion);  // Reutilizar el campo existente
            usuario.setExpiracionCodigoRecuperacion(LocalDateTime.now().plusHours(1));
            usuarioRepository.save(usuario);

            // Enviar el correo con el código de recuperación
            enviarCorreoCodigoRecuperacion(usuario.getCorreo(), codigoRecuperacion);
        } else {
            // Manejar el caso en el que el correo no esté registrado
            throw new UsuarioNoEncontradoException("El correo no está registrado");
        }
    }


}
