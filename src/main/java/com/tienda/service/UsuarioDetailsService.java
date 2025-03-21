/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.service;

import com.tienda.domain.Rol;
import com.tienda.domain.Usuario;
import com.tienda.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author anndy
 */
@Service("userDetailsService")
public class UsuarioDetailsService implements UserDetailsService{

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private HttpSession session;
    
    @Override
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String username) 
            throws UsernameNotFoundException {
        //Se busca el usuario de la tabla de usuario...
        Usuario usuario=usuarioRepository.findByUsername(username);
        
        //Se valida si se encuentra el usuario
        if(usuario==null){
            //El usuario con ese username, no se encontró
            throw new UsernameNotFoundException(username);
        }
        
        //Si estamos acá, entonces SI se encontró el usuario
        session.removeAttribute("usuarioImagen");
        session.setAttribute("usuarioImagen", usuario.getRutaImagen());
        
        //Ahora, se cargan los roles del usuario
        var roles = new ArrayList<GrantedAuthority>();
        for(Rol rol : usuario.getRoles()){
            roles.add(new SimpleGrantedAuthority("ROLE_"+rol.getNombre()));
        }
        
        //Se retorna el usuario con usernam, password y roles...
        
        return new User(usuario.getUsername(),usuario.getPassword(),roles);
    }
    
}
