package com.example.imdbRating.controller;

import com.example.imdbRating.entity.Movie;
import com.example.imdbRating.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    // Display all movies
    @GetMapping
    public String listMovies(Model model) {
        model.addAttribute("movies", movieRepository.findAll());
        return "list-movies";
    }

    // Display a form to add a new movie
    @GetMapping("/add")
    public String showAddMovieForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "add-movie";
    }

    // Process the form for adding a new movie
    @PostMapping
    public String addMovie(@ModelAttribute Movie movie) {
        movieRepository.save(movie);
        return "redirect:/movies";
    }

    // Display a form to edit an existing movie
    @GetMapping("/edit/{id}")
    public String showEditMovieForm(@PathVariable Long id, Model model) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        model.addAttribute("movie", movie);
        return "edit-movie";
    }

    // Process the form for editing a movie
    @PostMapping("/{id}")
    public String updateMovie(@PathVariable Long id, @ModelAttribute Movie movie, Model model) {
        Movie existingMovie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        existingMovie.setTitle(movie.getTitle());
        existingMovie.setDescription(movie.getDescription());
        movieRepository.save(existingMovie);
        return "redirect:/movies";
    }

    // Delete a movie
    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        movieRepository.delete(movie);
        return "redirect:/movies";
    }
}
