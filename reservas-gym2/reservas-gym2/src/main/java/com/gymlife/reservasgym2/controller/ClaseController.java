package com.gymlife.reservasgym2.controller;

import com.gymlife.reservasgym2.model.Clase;
import com.gymlife.reservasgym2.repository.ClaseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/clases")
@PreAuthorize("hasRole('ADMIN')")
public class ClaseController {
    private final ClaseRepository claseRepository;

    public ClaseController(ClaseRepository claseRepository) {
        this.claseRepository = claseRepository;
    }

    @PostMapping
    public Clase crearClase(@RequestBody Clase clase) {
        return claseRepository.save(clase);
    }

    @GetMapping
    public List<Clase> listarClases() {
        return claseRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Clase> actualizarClase(@PathVariable Long id, @RequestBody Clase claseDetalles) {
        Optional<Clase> claseOptional = claseRepository.findById(id);
        if (claseOptional.isPresent()) {
            Clase clase = claseOptional.get();
            clase.setNombre(claseDetalles.getNombre());
            clase.setDescripcion(claseDetalles.getDescripcion());
            clase.setCuposDisponibles(claseDetalles.getCuposDisponibles());
            return ResponseEntity.ok(claseRepository.save(clase));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/usuarios")
    public String verUsuariosEnClase(@PathVariable Long id, Model model) {
        Optional<Clase> claseOptional = claseRepository.findById(id);
        if (claseOptional.isPresent()) {
            model.addAttribute("clase", claseOptional.get());
            return "usuarios_clase"; // Página donde se mostrarán los usuarios
        }
        return "redirect:/admin/clases";
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarClase(@PathVariable Long id) {
        if (claseRepository.existsById(id)) {
            claseRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
