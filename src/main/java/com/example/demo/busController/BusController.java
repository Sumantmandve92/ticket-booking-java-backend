package com.example.demo.busController;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.Deflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.MSRTCSecurity.BusOwnerImplementsUserDetails;
import com.example.demo.busEntity.Bus;
import com.example.demo.busEntity.BusOwner;
import com.example.demo.busEntity.BusRoute;
import com.example.demo.busEntity.File;
import com.example.demo.busRepository.BusOwnerRepo;
import com.example.demo.busRepository.BusRepo;
import com.example.demo.busRepository.BusRouteRepo;
import com.example.demo.busRepository.FileRepo;
import com.example.demo.jwtSecurity.JwtUtil;
import com.example.demo.jwtSecurity.UserDetails;
import com.example.demo.projections.BusTimeTable;
import com.example.demo.travellerController.KeyWithStatus;
import com.example.demo.travellerEntity.Passanger;
import com.example.demo.travellerRepo.PassangerRepo;
import com.example.demo.travellerSecurity.PassengerImplementUserDetails;

import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.lang.Collections;

@RestController
@CrossOrigin
public class BusController {

	//	================================================================================
	@Autowired
	private JavaMailSender mailsender;
	@Autowired
	BusRouteRepo busRouteRepo;
	@Autowired
	BusRepo busRepo;
	@Autowired
	BusOwnerRepo busownerRepo;
	@Autowired
	BusOwner busowner;
	@Autowired
	PassangerRepo passRepo;
	@Autowired
	JwtUtil jwt;
	//	================================================================================
	//	Handler
	@RequestMapping("busOwnerRegistration")
	public int registerBusOwner(@RequestBody BusOwner p1) 
	{
		try {
			byte cnt=busownerRepo.countByUsername(p1.getUsername());
			if(cnt==0) 
			{
				busownerRepo.save(p1);
				return 1;
			}
			else 
			{
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	@RequestMapping("busOwnerLogin")
	public KeyWithStatus  busOwnerLogin(@RequestBody BusOwner p1) 
	{

		try {
			byte cnt=busownerRepo.countByUsername(p1.getUsername());

			if(cnt==1) 
			{
				BusOwner p2=busownerRepo.findByUsername(p1.getUsername());
				if(p2.getPassword().equals(p1.getPassword())) 
				{
					//					login successsfully
					busowner.setPassword(p1.getPassword());
					busowner.setUsername(p1.getUsername());
					UserDetails userDetails=new BusOwnerImplementsUserDetails(busowner);
					String tokan=jwt.generateToken(userDetails);
					System.out.println(tokan);
					System.out.println(jwt.extractUsername(tokan));
					return new KeyWithStatus(1,tokan);

				}
				else 
				{
					//					wrong password
					return new KeyWithStatus(-1,null);
				}
			}
			else 
			{
				//				wrong username
				return new KeyWithStatus(-2,null);
			}
		} catch (Exception e) {

			e.printStackTrace();
			return new KeyWithStatus(0,null);
		}

	}
	//	==================================================================================
	@RequestMapping("getTodaysBuses/{fromstation}/{tostation}/{date}")
	public List<BusRoute> getTodaysBuses(@PathVariable String fromstation,@PathVariable String tostation,@PathVariable String date) {
		try {
			return busRouteRepo.getTodaysBuses(fromstation,tostation,date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping("a2ddnewBus/{tokan}")
	public byte addBus(@RequestBody Bus bus,@PathVariable String tokan) 
	{
		try {
			String username=jwt.extractUsername(tokan);
			bus.setRegistereddate(LocalDateTime.now().toString());
			bus.setBusownerid(username);
			System.out.println("hi");
			byte cnt=busRepo.countByBusnumber(bus.getBusnumber());
			if(cnt==0)
			{
				busRepo.save(bus);
				//				 bus registered successfully
				return 1;
			}
			else 
			{
				//				already registered
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			//			SWR
			return 0;
		}

	}
	@RequestMapping("g2etMyAllBuses/{tokan}")
	public List<Bus> getAllBuses(@PathVariable String tokan)
	{
		try {
			String username=jwt.extractUsername(tokan);
			return busRepo.findByBusownerid(username);
		} catch (Exception e) {
			return null;
		}

	}
	@RequestMapping("r2outeOfThisBus/{busnumber}/{tokan}")
	public int routeOfThisBus(@PathVariable String busnumber,@PathVariable String tokan,@RequestBody BusRoute busRoute) 
	{
		try {
			byte cnt=busRouteRepo.findByBusAndDate(busRoute.getDate(),busnumber);
			if(cnt==0) 
			{
				Bus bus=busRepo.findById(busnumber).get();
				busRoute.setBus(bus);
				busRouteRepo.save(busRoute);
				//				Scheduled successfully
				return 1;
			}
			else 
			{
				//				bus is on route 
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			//			SWR
			return 0;
		}
	}
	@RequestMapping("confirmbyotp/{email}")
	public byte generateOtp(@PathVariable String email)
	{
		try {
			byte cnt=passRepo.countByEmail(email);
			if(cnt==1) 
			{
				int otp=(int)(Math.random()*10000);
				Passanger passanger=passRepo.findByEmail(email);
				passanger.setOtp(otp);
				passRepo.save(passanger);
				sendEmail(email,"Your One time password is: "+otp,"Hi ");
				return 1;
			}
			else 
			{
				//				not registered email
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	@RequestMapping("bookTicket/{seatnumber}/{busRouteId}/{email}/{otp}")
	public byte bookTicket(@PathVariable int seatnumber, @PathVariable int busRouteId, @PathVariable String email,@PathVariable int otp ) 
	{
		try {
			System.out.println("h3");
			Passanger passanger=passRepo.findByEmail(email);
			if(otp==passanger.getOtp()) 
			{
				BusRoute r1=busRouteRepo.findById(busRouteId).get();
				r1.getPassangers()[seatnumber]=passanger.getId()+"";
				busRouteRepo.save(r1);
				sendEmail(email,"Your ticket number is :"+r1.getBus().getBusnumber()+":"+r1.getBus().getAcnoac()+":"
						+ ""+r1.getBus().getBuscategory()+":Seatnumber="+(seatnumber+1)+" from "+r1.getFromstation()+""
						+ " at "+r1.getTakeoffat()+" to "+r1.getTostation()+" with price "+r1.getTicketprice(),"Hi "+passanger.getFullname());
				return 1;
			}
			else 
			{
				//otp not matched
				return -1;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;		
		}	
	}

	public void sendEmail(String toEmail,String body,String subject) 
	{
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom("swrshoppingmall2022@gmail.com");
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);
		mailsender.send(message);
		System.out.println(message);

	}
	@RequestMapping("g2etTimeTableOfThisBus/{busnumber}/{tokan}")
	public List<BusTimeTable> g2etTimeTableOfThisBus(@PathVariable String busnumber,@PathVariable String tokan) 
	{
		try {
		
			LocalDateTime ldt=LocalDateTime.now();
			String date=ldt.toString().substring(0,10);
			return busRouteRepo.g2etTimeTableOfThisBus(busnumber,date);
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}
//	=================================================================================================================================================
	@Autowired
	FileRepo fileRepo;
	
	@RequestMapping("u2ploadfile/{tokan}")
	public BodyBuilder uploadImage(@RequestParam("imageFile") MultipartFile file,@PathVariable String tokan) throws IOException
	{
		try {
			System.out.println("Original image byte size : "+file.getBytes().length);
			File img=new File(0,file.getOriginalFilename(),file.getContentType(),compressedBytes(file.getBytes()));
			fileRepo.save(img);
			return ResponseEntity.status(HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	private byte[] compressedBytes(byte[] bytes) {
	
		Deflater deflater=new Deflater();
		deflater.setInput(bytes);
		deflater.finish();
		ByteArrayOutputStream outputStream= new ByteArrayOutputStream(bytes.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (Exception e) {
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

		return outputStream.toByteArray();
		
	}
//	=================================================================================================================================================


}
