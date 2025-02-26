package com.gymlife.reservasgym2.controller;

import com.gymlife.reservasgym2.model.Clase;
import com.gymlife.reservasgym2.model.Reserva;
import com.gymlife.reservasgym2.model.Usuario;
import com.gymlife.reservasgym2.repository.ClaseRepository;
import com.gymlife.reservasgym2.repository.ReservaRepository;
import com.gymlife.reservasgym2.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user/reservas")
@PreAuthorize("hasRole('USER')")
public class ReservaController {
    private final ReservaRepository reservaRepository;
    private final ClaseRepository claseRepository;
    private final UsuarioRepository usuarioRepository;

    public ReservaController(ReservaRepository reservaRepository, ClaseRepository claseRepository, UsuarioRepository usuarioRepository) {
        this.reservaRepository = reservaRepository;
        this.claseRepository = claseRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/clases")
    public List<Clase> listarClasesDisponibles() {
        return claseRepository.findAll();
    }

    @PostMapping("/reservar/{claseId}")
    public ResponseEntity<String> reservarClase(@PathVariable Long claseId, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByUsername(userDetails.getUsername());
        Optional<Clase> claseOptional = claseRepository.findById(claseId);

        if (usuarioOptional.isPresent() && claseOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            Clase clase = claseOptional.get();

            if (clase.getCuposDisponibles() > 0) {
                Reserva reserva = new Reserva();
                reserva.setUsuario(usuario);
                reserva.setClase(clase);
                reservaRepository.save(reserva);

                clase.setCuposDisponibles(clase.getCuposDisponibles() - 1);
                claseRepository.save(clase);

                return ResponseEntity.ok("Reserva realizada con éxito");
            } else {
                return ResponseEntity.badRequest().body("No hay cupos disponibles");
            }
        }
        return ResponseEntity.notFound().build();
    }
}
