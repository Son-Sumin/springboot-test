package com.bitacademy.cocktail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bitacademy.cocktail.domain.Cocktail;;

@Repository
public interface CocktailRepository extends JpaRepository<Cocktail, Long> {
	
	Cocktail findByNo(Long no);
	
}
