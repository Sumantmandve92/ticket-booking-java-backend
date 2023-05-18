package com.example.demo.busRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.busEntity.Bus;

public interface BusRepo extends JpaRepository<Bus, String>{

	byte countByBusnumber(String busnumber);

	List<Bus> findByBusownerid(String username);

	

}
