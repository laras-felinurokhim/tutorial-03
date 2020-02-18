package com.apap.tu03.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tu03.model.MovieModel;
import com.apap.tu03.service.MovieService;

@Controller
public class MovieController {

	@Autowired
	private MovieService movieService;
	
	@RequestMapping("/movie/add")
	public String add(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "genre", required = true) String genre,
			@RequestParam(value = "budget", required = true) Long budget,
			@RequestParam(value = "duration", required = true) Integer duration) {
		
		MovieModel movie = new MovieModel(id, title, genre, budget, duration);
		movieService.addMovie(movie);
		return "add";
		
	}
	
	/*
	 * bagian ini di-comment terlebih dahulu karena crash dengan path variabel untuk view juga
	 * buka tag comment untuk menjalankan, dan tambahkan tag comment pada path variable di method pathView
	 */
	/*@RequestMapping("/movie/view")
	public String view(@RequestParam("id") String id, Model model) {
		MovieModel archive = movieService.getMovieDetail(id);
		model.addAttribute("movie", archive);
		return "view-movie";
		
	}*/
	
	@RequestMapping("/movie/viewall")
	public String viewAll(Model model) {
		List<MovieModel> archive = movieService.getMovieList();
		model.addAttribute("movies", archive);
		return "viewall-movie";
		
	}
	
	
	/*
	 * tutup dengan tag comment jika akan menjalankan method view
	 */
	@RequestMapping(value= {"/movie/view","/movie/view/{id}"})
	public String viewPath(@PathVariable(name = "id", required = false) String idMovie, Model model) {
		if(idMovie == null) {
			model.addAttribute("data","Data Tidak Ditemukan");
		}else {
			MovieModel archive = movieService.getMovieDetail(idMovie);
			if(archive == null) {
				model.addAttribute("data","Data Tidak Ditemukan");
			}else {
				model.addAttribute("data","Data Ditemukan");
				model.addAttribute("movie", archive);
			}
		}		
		return "view";
	}
	
	@RequestMapping(value= {"/movie/update","/movie/update/{id}/duration/{durasi}"})
	public String updatePath(@PathVariable(name = "id", required = false) String idMovie, 
							 @PathVariable(name = "durasi", required = false) Integer durationMovie, 
			Model model) {
		if(idMovie == null) {
			model.addAttribute("data","Data Tidak Ditemukan");
		}else {
			MovieModel archive = movieService.getMovieDetail(idMovie);
			
			if(archive == null) {
				model.addAttribute("data","Data Tidak Ditemukan. Update dibatalkan!");
			}else {
				archive.setDuration(durationMovie);
				model.addAttribute("data","Data Berhasil di-Update!");
				model.addAttribute("movie", archive);
			}
		}		
		return "view-update";
	}
	
	@RequestMapping(value= {"/movie/delete","/movie/delete/{id}"})
	public String deletePath(@PathVariable(name = "id", required = false) String idMovie, 
			Model model) {
		if(idMovie == null) {
			model.addAttribute("data","Data Tidak Ditemukan");
		}else {
			MovieModel archive = movieService.getMovieDetail(idMovie);
			if(archive == null) {
				model.addAttribute("data","Data Tidak Ditemukan. Delete dibatalkan!");
			}else {
				archive = null;
				model.addAttribute("data","Data Berhasil di-Delete!");
				model.addAttribute("movie", archive);
			}
		}		
		return "view-delete";
	}
	
	
}
