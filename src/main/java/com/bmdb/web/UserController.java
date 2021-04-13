package com.bmdb.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bmdb.business.User;
import com.bmdb.db.UserRepo;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserRepo UserRepo;
	
	@GetMapping("/")
	public List<User> getAll(){
		return UserRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public User getById(@PathVariable int id){
		return UserRepo.findById(id).get();
	}
	
	@PostMapping("/")
	public User create(@RequestBody User user) {
		return UserRepo.save(user);
		
	}
	
	@PutMapping("/")
	public User update(@RequestBody User user) {
		return UserRepo.save(user);
		
	}	
	@DeleteMapping("/{id}")
	public User delete(@PathVariable int id) {
		Optional<User> user = UserRepo.findById(id);
		if (user.isPresent()) {
			UserRepo.delete(user.get());
		}
		else {
			System.out.println("delete error, User not found for id: "+id);
		}
		return user.get();
		
	}
	
	//greg prefers getmapping, authenticating via get
	@GetMapping("")
	public User login (@RequestParam String username, @RequestParam String password){
		return UserRepo.findByUsernameAndPassword(username, password);
	}
	//sean prefers post, authenticating using post	
	@PostMapping("/login")
	public User login(@RequestBody User u) {
		return UserRepo.findByUsernameAndPassword(u.getUsername(),u.getPassword());
	}
	
}

