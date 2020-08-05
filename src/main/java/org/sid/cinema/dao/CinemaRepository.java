package org.sid.cinema.dao;


import java.util.List;

import org.sid.cinema.entities.Cinema;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
@RepositoryRestResource
@CrossOrigin("*")
public interface CinemaRepository extends JpaRepository<Cinema, Long> {

	public List<Cinema> findAll();
	@Query("select c from Cinema c where c.name like :x")
	public Page<Cinema>findByNameContains(@Param("x")String mc,Pageable peagable);
	@Query("select c from Cinema c where c.id = :x")
	public Cinema findOne(@Param("x")Long id );
}
