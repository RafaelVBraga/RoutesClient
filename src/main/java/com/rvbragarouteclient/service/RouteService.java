package com.rvbragarouteclient.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rvbragarouteclient.domain.PlannedStop;
import com.rvbragarouteclient.domain.Route;

@Service
public class RouteService {

	public List<Route> findAll() {

		List<Route> list = new ArrayList<Route>();
		RestTemplate restTemplate = new RestTemplate();
		RequestEntity<Void> request = RequestEntity.get(URI.create("http://localhost:8081/routes")).build();
		try {
			ResponseEntity<Route[]> response = restTemplate.exchange(request, Route[].class);
			for (Route route : response.getBody()) {
				list.add(route);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Servidor offline!");
			return null;
		}

		return list;
	}

	public Route findById(Long id) {
		RestTemplate restTemplate = new RestTemplate();
		RequestEntity<Void> request = RequestEntity.get(URI.create("http://localhost:8081/route/" + id)).build();
		try {
			ResponseEntity<Route> response = restTemplate.exchange(request, Route.class);
			return response.getBody();
		} catch (Exception e) {
			System.out.println("Servidor offline!");
			return null;
		}

	}

	public String saveRoute(Route route) {
		RestTemplate restTemplate = new RestTemplate();

		RequestEntity<Route> request = RequestEntity.post(URI.create("http://localhost:8081/route")).body(route);

		try {
			ResponseEntity<Void> response = restTemplate.exchange(request, Void.class);
			return response.getHeaders().getLocation().toString();
		} catch (Exception e) {
			System.out.println("Servidor offline!");
			return "Servidor offline!";
		}

	}
	
	public String deletePlannedStop(PlannedStop plannedStop, Long id) {
		RestTemplate restTemplate = new RestTemplate();

		RequestEntity<PlannedStop> request = ((BodyBuilder) RequestEntity.delete(URI.create("http://localhost:8081/route/plannedStop/"+id)).accept(MediaType.APPLICATION_JSON)).body(plannedStop);

		try {
			ResponseEntity<String> response = restTemplate.exchange(request, String.class);
			return response.getBody();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Servidor offline!");
			return "Servidor offline!";
		}

	}

	public String createFile() {
		RestTemplate restTemplate = new RestTemplate();

		RequestEntity<Void> request = RequestEntity.post(URI.create("http://localhost:8081/routes-file")).build();
		try {
		ResponseEntity<String> response = restTemplate.exchange(request, String.class);
		return response.getBody().toString();
		}catch(Exception e) {
			System.out.println("Servidor offline!");
			return "Servidor offline!";
		}
	}
	public String loadFile() {
		RestTemplate restTemplate = new RestTemplate();

		RequestEntity<Void> request = RequestEntity.get(URI.create("http://localhost:8081/routes-file")).build();
		try {
		ResponseEntity<String> response = restTemplate.exchange(request, String.class);
		return response.getBody().toString();
		}catch(Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Servidor offline!");
			return "Servidor offline!";
		}
	}

}
