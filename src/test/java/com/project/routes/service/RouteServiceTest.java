package com.project.routes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.project.routes.util.GraphLoaderUtil;

@ExtendWith(MockitoExtension.class)
class RouteServiceTest {

    @Mock
    private GraphLoaderUtil graphLoader;

    @InjectMocks
    private RouteService routeService;

    private Map<String, List<String>> graph;

    @BeforeEach
    void setUp() {
        graph = new HashMap<>();

        graph.put("PRT", List.of("ESP"));
        graph.put("ESP", List.of("PRT", "FRA"));
        graph.put("FRA", List.of("ESP", "DEU"));
        graph.put("DEU", List.of("FRA"));
        graph.put("ISL", List.of()); // no borders
    }

    // ----------------------------
    // 1️⃣ Route exists
    // ----------------------------
    @Test
    void shouldReturnRoute_whenRouteExists() {
        Mockito.when(graphLoader.getFullGraph()).thenReturn(graph);

        List<String> route = routeService.findRoute("PRT", "DEU");

        assertEquals(List.of("PRT", "ESP", "FRA", "DEU"), route);
    }

    // ----------------------------
    // 2️⃣ No land route
    // ----------------------------
    @Test
    void shouldThrowBadRequest_whenNoRouteExists() {
        Mockito.when(graphLoader.getFullGraph()).thenReturn(graph);

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> routeService.findRoute("PRT", "ISL")
        );

        assertEquals(HttpStatus.BAD_REQUEST.value(), ex.getRawStatusCode());
        assertEquals("No land route found", ex.getReason());
    }

    // ----------------------------
    // 3️⃣ Same origin & destination
    // ----------------------------
    @Test
    void shouldReturnSingleCountry_whenOriginEqualsDestination() {
        Mockito.when(graphLoader.getFullGraph()).thenReturn(graph);

        List<String> route = routeService.findRoute("PRT", "PRT");

        assertEquals(List.of("PRT"), route);
    }

    // ----------------------------
    // 4️⃣ Invalid country code
    // ----------------------------
    @Test
    void shouldThrowBadRequest_whenInvalidCountryCode() {
        Mockito.when(graphLoader.getFullGraph()).thenReturn(graph);

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> routeService.findRoute("XXX", "DEU")
        );

        assertEquals(HttpStatus.BAD_REQUEST.value(), ex.getRawStatusCode());
        assertEquals("Invalid country code", ex.getReason());
    }
}