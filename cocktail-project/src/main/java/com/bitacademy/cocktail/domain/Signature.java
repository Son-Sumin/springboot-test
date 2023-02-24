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
import javax.persistence.Table;

import com.bitacademy.cocktail.base.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name="signature")
@Table
@Data
@Builder
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor 
public class Signature extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no;

	@Column(name = "cocktail_name")
	private String cocktailName;

	@Column(name = "cocktail_contents")
	private String cocktailContents;

	@Column(name = "recipe_contents")
	private String recipeContents;

	private String type;
	
	private Integer hit;
	
	@ToString.Exclude
	@Builder.Default
	@OneToMany(mappedBy = "signature", cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"signature"})
	private List<ReviewSignature> reviewSignatures = new ArrayList<>();
	
	@ToString.Exclude
	@Builder.Default
	@OneToMany(mappedBy = "signature", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	@JsonIgnoreProperties({"signature"})
	private List<SignatureImage> signatureImages = new ArrayList<>();
	
}



//	public void addSignatureRecipe(SignatureRecipe signatureRecipe){
//		signatureRecipes.add(signatureRecipe);
//		signatureRecipe.setSignature(this);
//    }
	
//	@Builder
//    private Signature(String nickname, String cocktailName, String cocktailContents,
//    					String recipeContents, String type, Integer hit, Integer like) {
//        this.nickname = nickname;
//        this.cocktailName = cocktailName;
//        this.cocktailContents = cocktailContents;
//        this.recipeContents = recipeContents;
//        this.type = type;
//        this.hit = hit;
//        this.like = like;
//    }

//@PrePersist
//public void prePersistHit() {
//  this.hit = this.hit == null ? 0 : this.hit;
//}
//
//@PrePersist
//public void prePersistLike() {
//  this.like = this.like == null ? 0 : this.like;
//}
