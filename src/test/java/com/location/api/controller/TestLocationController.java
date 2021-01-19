package com.location.api.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.location.api.comparator.Point2DComparator;
import com.location.api.exception.CircleInsideAnotherException;
import com.location.api.exception.NoSuchAlgorithmException;
import com.location.api.exception.TwoDimensionalTrilaterationException;
import com.quasar.api.core.model.Point2D;
import com.quasar.api.core.request.LocationRequest;

@SpringBootTest
@AutoConfigureMockMvc
class TestLocationController {

	@Autowired
	LocationController locationController;

	@Autowired
	MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();

	@Value("classpath:/requests/noSolutionRequest.json")
	Resource noSolutionRequestResource;

	@Value("classpath:/requests/validSolutionRequest.json")
	Resource validSolutionRequestResource;

	@Value("classpath:/requests/emptyRequest.json")
	Resource emptyRequestResource;

	@Value("classpath:/requests/requestWithoutDistances.json")
	Resource requestWithoutDistancesResource;

	@Value("classpath:/requests/requestWithoutPoints.json")
	Resource requestWithoutPointsResource;

	@Test
	void testNoSolutionRequest() throws JsonMappingException, JsonProcessingException, IOException {
		LocationRequest noSolutionRequestData = objectMapper.readValue(
				new String(Files.readAllBytes(this.noSolutionRequestResource.getFile().toPath())),
				LocationRequest.class);
		assertThrows(CircleInsideAnotherException.class, () -> {
			locationController.locate(noSolutionRequestData);
		});
	}

	@Test
	void testSolutionRequest() throws TwoDimensionalTrilaterationException, NoSuchAlgorithmException,
			JsonMappingException, JsonProcessingException, IOException {
		LocationRequest validSolutionRequestData = objectMapper.readValue(
				new String(Files.readAllBytes(this.validSolutionRequestResource.getFile().toPath())),
				LocationRequest.class);
		assertTrue(Point2DComparator.AreEquals(new Point2D(-200, 0),
				locationController.locate(validSolutionRequestData).getLocation()));
	}

	@Test
	void testEmptyRequest() throws Exception {
		String json = new String(Files.readAllBytes(this.emptyRequestResource.getFile().toPath()));
		mockMvc.perform(MockMvcRequestBuilders.post("/locations").content(json).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	void testRequestWithoutDistances() throws Exception {
		String json = new String(Files.readAllBytes(this.requestWithoutDistancesResource.getFile().toPath()));
		mockMvc.perform(MockMvcRequestBuilders.post("/locations").content(json).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.distances", Is.is("distances field can not be missing")))
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	void testRequestWithoutPoints() throws Exception {
		String json = new String(Files.readAllBytes(this.requestWithoutPointsResource.getFile().toPath()));
		mockMvc.perform(MockMvcRequestBuilders.post("/locations").content(json).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.points", Is.is("points field can not be missing")))
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

}
