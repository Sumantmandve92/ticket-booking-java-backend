package com.example.demo.busRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.busEntity.BusOwner;

public interface BusOwnerRepo extends JpaRepository<BusOwner, Integer>{

	byte countByUsername(String username);

	BusOwner findByUsername(String username);

}
