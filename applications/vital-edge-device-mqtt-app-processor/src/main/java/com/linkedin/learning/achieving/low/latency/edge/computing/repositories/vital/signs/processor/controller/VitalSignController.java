package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor.controller;

import nyla.solutions.core.util.Digits;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
public class VitalSignController
{
	private Digits digits = new Digits();

	@CrossOrigin	
	@RequestMapping(value="/vital_rate")
	@ResponseBody
	public void vitalRate(HttpServletRequest request, HttpServletResponse response)
	throws IOException
	{
	
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/event-stream");
		response.setHeader("Cache-Control","no-cache");
		response.setCharacterEncoding("UTF-8");
		
		for(int i =0; i< 20 ; i++)
	    {

	    	String id = "1";
	    	String vitalId = id;
			String statName = "heartRate";
			double value = digits.generateInteger(50,90);
			double bodyTemperature = digits.generateInteger(90,120);
			response.getWriter().println("data: {\"vitalId\":\""+vitalId+"\",\"id\":\""+id+"\", \"statName\":\""+statName+"\", \"value\": "+value+"}\r\n");

			statName = "bodyTemperature";
			response.getWriter().println("data: {\"vitalId\":\""+vitalId+"\",\"id\":\""+id+"\", \"statName\":\""+statName+"\", \"value\": "+value+"}\r\n");
	    }
	}



}
