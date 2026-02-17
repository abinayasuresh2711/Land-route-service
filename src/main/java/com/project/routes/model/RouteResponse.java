package com.project.routes.model;

import java.util.List;


public class RouteResponse {
	private List<String> route;

	public RouteResponse(List<String> routes) {
		super();
		this.route = routes;
	}

	public List<String> getRoutes() {
		return route;
	}

	public void setRoutes(List<String> routes) {
		this.route = routes;
	}
}
