package com.project.routes.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.project.routes.util.GraphLoaderUtil;

@Service
public class RouteService {

	private final GraphLoaderUtil graphLoader;

	public RouteService(GraphLoaderUtil graphLoader) {
		this.graphLoader = graphLoader;
	}

	public List<String> findRoute(String origin, String destination) {
		Map<String, List<String>> fullGraph = graphLoader.getFullGraph();

		if (origin.equals(destination)) {
			return List.of(origin);
		}

		if (!fullGraph.containsKey(origin) || !fullGraph.containsKey(destination)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid country code");
		}

		Queue<String> queue = new LinkedList<>();
		Set<String> visited = new HashSet<>();
		Map<String, String> parent = new HashMap<>();

		queue.add(origin);
		visited.add(origin);

		while (!queue.isEmpty()) {
			String current = queue.poll();

			for (String neighbour : fullGraph.get(current)) {
				if (!visited.contains(neighbour)) {
					visited.add(neighbour);
					parent.put(neighbour, current);

					if (neighbour.equals(destination)) {
						return buildResponse(parent, destination);
					}

					queue.add(neighbour);
				}
			}
		}

		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No land route found");

	}

	private List<String> buildResponse(Map<String, String> parent, String destination) {
		LinkedList<String> path = new LinkedList<>();
		String current = destination;
		while (current != null) {
			path.addFirst(current);
			current = parent.get(current);
		}
		return path;
	}

}
