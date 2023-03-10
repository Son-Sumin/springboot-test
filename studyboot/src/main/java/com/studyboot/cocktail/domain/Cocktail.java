package com.studyboot.cocktail.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Cocktail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;
	private String type;
	private String name;
	@Column(name = "eng_name")
	private String engName;
	private String image;
//	@Column(name = "cocktail_contents")
//	private String cocktailContents;
//	@Column(name = "recipe_contents")
//	private String recipeContents;
	
//	public Cocktail(String type, String name, String engName, String image) {
//        this.type = type;
//        this.name = name;
//        this.engName = engName;
//        this.image = image;
//    }
}
