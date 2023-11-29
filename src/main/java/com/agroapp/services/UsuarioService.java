package com.agroapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agroapp.models.Usuario;
import com.agroapp.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
    UsuarioRepository usuarioRepository;

    public int registerNewUserServiceMethod(String nombreUsuario, String correo, String contraseña){
        return usuarioRepository.registerNewUser(nombreUsuario,correo,contraseña);
    }
    // End Of Register New User Service Method.

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
}
