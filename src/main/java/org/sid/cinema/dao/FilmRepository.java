package org.sid.cinema.dao;

import java.util.List;

import org.sid.cinema.entities.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;
@RepositoryRestResource
@CrossOrigin("*")
public interface FilmRepository extends JpaRepository<Film, Long> {

	public List<Film> findAll();
	@Query("select f from Film f where f.titre like :x")
	public Page<Film>findByNameContains(@Param("x")String mc,Pageable peagable);
	@Query("select f from Film f where f.id = :x")
	public Film findOne(@Param("x")Long id );
	
	
}
