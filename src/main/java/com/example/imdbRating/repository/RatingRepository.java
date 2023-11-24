package com.example.imdbRating.repository;

import com.example.imdbRating.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    // Custom query methods can be added here, if needed
    // For example, to find ratings for a specific movie
}
