package com.gymlife.reservasgym2.repository;

import com.gymlife.reservasgym2.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Rol findByNombre(String nombre);
}
