package org.sid.cinema.entities;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Projection {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dateProjection;
	private double prix;
	@ManyToOne
	private Salle salle;
	@ManyToOne
	private Film film;
	@OneToMany(mappedBy = "projection",cascade = CascadeType.ALL)
	private Collection<Ticket> tickets;
	@ManyToOne
	private Seance seance;
}
