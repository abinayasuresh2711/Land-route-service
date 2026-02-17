package com.project.routes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.routes.model.RouteResponse;
import com.project.routes.service.RouteService;

@RestController
@RequestMapping("routing")
public class RouteController {
	@Autowired
	private RouteService routeService;

	@GetMapping("/{origin}/{destination}")
	public ResponseEntity<RouteResponse> getRoute(@PathVariable String origin, @PathVariable String destination) {
		List<String> route = routeService.findRoute(origin, destination);
		return ResponseEntity.ok(new RouteResponse(route));
	}
}
