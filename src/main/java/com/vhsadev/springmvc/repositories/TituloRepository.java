package com.vhsadev.springmvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vhsadev.springmvc.models.Titulo;

@Repository
public interface TituloRepository extends JpaRepository<Titulo, Long> {

}
