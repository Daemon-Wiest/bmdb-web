package com.bmdb.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bmdb.business.MovieCollection;
import com.bmdb.business.User;
import com.bmdb.db.MovieCollectionRepo;
import com.bmdb.db.UserRepo;

@CrossOrigin
@RestController
@RequestMapping("/api/movie-collections")
public class MovieCollectionController {
	@Autowired
	private MovieCollectionRepo MovieCollectionRepo;
	@Autowired
	private UserRepo UserRepo;
	@GetMapping("/")
	public List<MovieCollection> getAll(){
		return MovieCollectionRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public MovieCollection getById(@PathVariable int id){
		return MovieCollectionRepo.findById(id).get();
	}
	
	@PostMapping("/")
	public MovieCollection addMovieCollection(@RequestBody MovieCollection mc) {
	MovieCollectionRepo.save(mc);//save mc
	recalculateCollectionValue(mc);
	return mc;
	}
	
	private void recalculateCollectionValue(MovieCollection mc) {
		User u = mc.getUser();
		//gets user, declares it a variable
				//recalculate example
				//save mc, get all mc for this user, declare newtotal = 0, loop through mc, add purchaseprice to newtotal, set newtotal in user, save user
	List<MovieCollection> mcs = MovieCollectionRepo.findAllByUserId(u.getId());//get all mc for one user
	double newTotal= 0.0; // declare newtotal, equal to 0
	for (MovieCollection collection:mcs) { // loop through mc
		newTotal+=collection.getPurchasePrice();//add purchaseprice to newtotal
	}
	u.setCollectionValue(newTotal);//set newtotal in user
	UserRepo.save(u);
	}
	
	@PutMapping("/")
	public MovieCollection update(@RequestBody MovieCollection MovieCollection) {
		return MovieCollectionRepo.save(MovieCollection);
		
	}	
	@DeleteMapping("/{id}")
	public MovieCollection delete(@PathVariable int id) {
		Optional<MovieCollection> MovieCollection = MovieCollectionRepo.findById(id);
		if (MovieCollection.isPresent()) {
			MovieCollectionRepo.delete(MovieCollection.get());
		}
		else {
			System.out.println("delete error, MovieCollection not found for id: "+id);
		}
		return MovieCollection.get();
		
	}
	
	
	
}

