package com.gymlife.reservasgym2.controller;

import com.gymlife.reservasgym2.model.Rol;
import com.gymlife.reservasgym2.model.Usuario;
import com.gymlife.reservasgym2.repository.RolRepository;
import com.gymlife.reservasgym2.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(password));

        Rol userRole = rolRepository.findByNombre("USER");
        if (userRole == null) {
            userRole = new Rol();
            userRole.setNombre("USER");
            rolRepository.save(userRole);
        }

        usuario.setRoles(Collections.singleton(userRole));
        usuarioRepository.save(usuario);
        return "Usuario registrado";
    }
}
