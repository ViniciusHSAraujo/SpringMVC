package com.vhsadev.springmvc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vhsadev.springmvc.models.Titulo;

@Repository
public interface TituloRepository extends JpaRepository<Titulo, Long> {
	List<Titulo> findByDescricaoContaining(String busca);
}
