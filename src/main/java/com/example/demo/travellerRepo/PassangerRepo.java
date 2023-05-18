package com.example.demo.travellerRepo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.travellerEntity.Passanger;


public interface PassangerRepo extends JpaRepository<Passanger, String>{

	byte countByUsername(String username);

	byte countByEmail(String email);

	Passanger findByEmail(String email);

	Passanger findByUsername(String username);


}
