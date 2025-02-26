package com.gymlife.reservasgym2.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String adminPanel(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("usuario", user); // Pasar el usuario autenticado al modelo
        return "admin"; // No es necesario incluir `.html`, Spring lo busca en `templates`
    }
}
