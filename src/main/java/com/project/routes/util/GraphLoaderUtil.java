package com.project.routes.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class GraphLoaderUtil {
	Map<String, List<String>> fullGraph;
	
	@PostConstruct
	public Map<String, List<String>> init() {
		fullGraph = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		URL url;
		try {
			url = new URL("https://raw.githubusercontent.com/mledoze/countries/master/countries.json");
			InputStream is = url.openStream();
			JsonNode root = mapper.readTree(is);

			for (JsonNode country : root) {
				String cca3 = country.get("cca3").asText();
				List<String> borders = new ArrayList<>();
				JsonNode bordersNode = country.get("borders");
				if(!bordersNode.isEmpty())
				{	for (JsonNode border : bordersNode) {
						borders.add(border.asText());
					}
				}
				fullGraph.put(cca3, borders);
			}

		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fullGraph;
	}

	public Map<String, List<String>> getFullGraph() {
		return fullGraph;
	}
	
	
}
