package com.ealves.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ealves.model.Filme;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {
	
}
