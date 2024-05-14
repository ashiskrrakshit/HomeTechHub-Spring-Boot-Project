package com.project.hometechhub.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.hometechhub.entities.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
	Category findByCategoryName(String categoryName);

}
