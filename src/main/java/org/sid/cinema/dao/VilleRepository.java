package org.sid.cinema.dao;

import java.util.List;

import org.sid.cinema.entities.Ville;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
@RepositoryRestResource
@CrossOrigin("*")
public interface VilleRepository extends JpaRepository<Ville, Long>{

	public List<Ville> findAll();
	@Query("select v from Ville v where v.name like :x")
	public Page<Ville>findByNameContains(@Param("x")String mc,Pageable peagable);
	@Query("select v from Ville v where v.id = :x")
	public Ville findOne(@Param("x")Long id );
}
