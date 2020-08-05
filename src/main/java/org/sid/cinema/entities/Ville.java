package org.sid.cinema.entities;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Digits;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity

@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Ville {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	@Column(length = 75)
	
	private String name ;
	@DecimalMax("360")
	@Digits(integer=4,fraction = 2)
	private double longitude,lattitude,altitude;
    @OneToMany(mappedBy = "ville",cascade = CascadeType.ALL)
    private Collection<Cinema> cinemas; 
}
