package com.bitacademy.cocktail.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name="ingredient")
@Getter @Setter 
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;

	private String name;

	@Column(name = "eng_name")
	private String engName;
	
	private String type;
	
	private float degree;
	
	private String image;
	
	@ToString.Exclude
	@OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL)
	private List<CocktailRecipe> cocktailRecipe = new ArrayList<>();
	
//	@Builder
//	public Ingredient(String name, String engName, String type,
//						float degree, String image, CocktailRecipe cocktailRecipe) {
//      this.name = name;
//      this.engName = engName;
//      this.type = type;
//      this.name = name;
//      this.degree = degree;
//	}

}
