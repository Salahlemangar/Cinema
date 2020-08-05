package org.sid.cinema.dao;

import org.sid.cinema.entities.Projection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
@RepositoryRestResource
@CrossOrigin("*")
public interface ProjectionRepository extends JpaRepository<Projection, Long>{

	@Query("select p from Projection p where p.prix > :x")
	public Page<Projection>findBydoubleContains(@Param("x")double mc,Pageable peagable);
}
