package com.example.imdbRating.controller;

import com.example.imdbRating.entity.Rating;
import com.example.imdbRating.repository.MovieRepository;
import com.example.imdbRating.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private MovieRepository movieRepository;

    // Display all ratings
    @GetMapping
    public String listRatings(Model model) {
        model.addAttribute("ratings", ratingRepository.findAll());
        return "list-ratings";
    }

    // Display a form to add a new rating
    @GetMapping("/add")
    public String showAddRatingForm(Model model) {
        model.addAttribute("rating", new Rating());
        model.addAttribute("movies", movieRepository.findAll()); // Provide list of movies for the dropdown
        return "add-rating";
    }

    // Process the form for adding a new rating
    @PostMapping
    public String addRating(@ModelAttribute Rating rating) {
        ratingRepository.save(rating);
        return "redirect:/ratings";
    }

    // Display a form to edit an existing rating
    @GetMapping("/edit/{id}")
    public String showEditRatingForm(@PathVariable Long id, Model model) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rating not found"));
        model.addAttribute("rating", rating);
        model.addAttribute("movies", movieRepository.findAll()); // Provide list of movies for the dropdown
        return "edit-rating";
    }

    // Process the form for editing a rating
    @PostMapping("/{id}")
    public String updateRating(@PathVariable Long id, @ModelAttribute Rating rating, Model model) {
        Rating existingRating = ratingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rating not found"));
        existingRating.setScore(rating.getScore());
        existingRating.setMovie(rating.getMovie());
        ratingRepository.save(existingRating);
        return "redirect:/ratings";
    }

    // Delete a rating
    @GetMapping("/delete/{id}")
    public String deleteRating(@PathVariable Long id) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rating not found"));
        ratingRepository.delete(rating);
        return "redirect:/ratings";
    }
}
