package com.gymlife.reservasgym2.controller;

import com.gymlife.reservasgym2.model.Clase;
import com.gymlife.reservasgym2.repository.ClaseRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ThymeleafController {
    private final ClaseRepository claseRepository;

    public ThymeleafController(ClaseRepository claseRepository) {
        this.claseRepository = claseRepository;
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("usuario", userDetails);
        return "index"; // Redirige a index.html
    }

    @GetMapping("/clases")
    public String listarClases(Model model) {
        List<Clase> clases = claseRepository.findAll();
        model.addAttribute("clases", clases);
        return "clases"; // Redirige a clases.html
    }
}
