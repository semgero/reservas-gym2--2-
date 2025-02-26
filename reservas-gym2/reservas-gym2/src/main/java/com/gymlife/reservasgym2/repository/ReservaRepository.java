package com.gymlife.reservasgym2.repository;

import com.gymlife.reservasgym2.model.Reserva;
import com.gymlife.reservasgym2.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByUsuario(Usuario usuario);
}
