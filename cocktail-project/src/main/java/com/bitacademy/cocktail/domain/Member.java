package com.bitacademy.cocktail.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "member")
@Data
@EqualsAndHashCode(callSuper=false)
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no;
	@Column(nullable = false, length = 30)
	private String name;
	@Column(nullable = false, length = 30)
	private String id;
	@Column(nullable = false, length = 30)
	private String password;
	@Column(nullable = false, length = 30)
	private String nickname;
	private String birth;
	@Column(name = "phonenumber")
	private String phoneNumber;
	@Enumerated(EnumType.STRING)
	private Role role;
	@Column(name = "profile_image")
	private String profileImage;
	@Column(name="reg_date")
	private LocalDateTime createdAt;
	@Column(name="gender")
	private String gender;

	@OneToMany(mappedBy="member", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties({"member"})
	private List<Board> boards = new ArrayList<>();
	
	@OneToMany(mappedBy="member", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties({"member"})
	private List<ReviewBoard> reviews = new ArrayList<>();
	
	@OneToMany(mappedBy="member", cascade = CascadeType.REMOVE)
//	@JsonIgnoreProperties({"reviewSignatures", "signatureImages"})
	private List<Signature> signatures = new ArrayList<>();
	
	@OneToMany(mappedBy="member", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties({"signature"})
	@OrderBy("createdDate desc")
	private List<ReviewSignature> reviewSignatures = new ArrayList<>();
	
//	@OneToMany(mappedBy="member", cascade = CascadeType.REMOVE)
//	private List<LikeSignature> LikeSignature = new ArrayList<>();
	
    @PrePersist
    public void createdAt() {
        this.createdAt = LocalDateTime.now();
    }

}
