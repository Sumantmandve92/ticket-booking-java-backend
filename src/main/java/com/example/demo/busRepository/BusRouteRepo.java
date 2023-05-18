package com.example.demo.busRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.busEntity.Bus;
import com.example.demo.busEntity.BusRoute;
import com.example.demo.projections.BusTimeTable;

public interface BusRouteRepo extends JpaRepository<BusRoute, Integer> {

	@Query(value = "SELECT * FROM bus_route "
			+ "where fromstation=?1 and tostation=?2 and date=?3",nativeQuery = true)
	List<BusRoute> getTodaysBuses(String fromstation, String tostation, String date);
	@Query(value = "SELECT count(*) FROM bus_route where date=?1 and bus_busnumber=?2",nativeQuery = true)
	byte findByBusAndDate( String date,String busnumber);
	
	
	
	
	@Query(value = "SELECT id,fromstation,landon,takeoffat,tostation FROM bus_route where date>=?2 and bus_busnumber=?1",nativeQuery = true)
	List<BusTimeTable> g2etTimeTableOfThisBus(String busnumber, String date);

}
