package com.example.demo.travellerRepo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.travellerEntity.Profile;

public interface ProfileRepo extends JpaRepository<Profile, Integer>{

}
