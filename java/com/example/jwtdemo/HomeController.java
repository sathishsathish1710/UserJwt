package com.example.jwtdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwtdemo.services.MyUserDetailsService;
import com.example.jwtdemo.utils.JwtUtility;

import models.JwtRequest;
import models.JwtResponse;

@RestController
public class HomeController {
	
	
	
	 @Autowired
	    private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	
	@Autowired
	private JwtUtility jwtUtility;
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@GetMapping("/")
	public String home() {
		return "Wkjg ghjr vrjhv ";
	}
	@PostMapping("/authenticate")
	public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {

		 try {
		        authenticationManager.authenticate(
		                new UsernamePasswordAuthenticationToken(
		                       jwtRequest.getUsername(),
		                        jwtRequest.getPassword()
		                )
		        );
	        
	    } catch (BadCredentialsException e) {
	        throw new Exception("Invalid Credentials", e);
	    }

	    final UserDetails userDetails
	            = userDetailsService.loadUserByUsername(jwtRequest.getUsername());

	    final String token =
	            jwtUtility.generateToken(userDetails);

	    return new JwtResponse(token);
	}

}
