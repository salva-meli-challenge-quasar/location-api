package com.location.api.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.location.api.comparator.Point2DComparator;
import com.location.api.exception.TwoDimensionalTrilaterationException;
import com.quasar.api.core.model.Point2D;
import com.quasar.api.core.request.LocationRequest;

@SpringBootTest
class TestLocationController {

	@Autowired
	LocationController locationController;

	ObjectMapper objectMapper = new ObjectMapper();

	@Value("classpath:/requests/noSolutionRequest.json")
	Resource noSolutionRequestResource;

	@Value("classpath:/requests/validSolutionRequest.json")
	Resource validSolutionRequestResource;

	@Test
	void testNoSolutionRequest() throws Exception {
		LocationRequest noSolutionRequestData = objectMapper.readValue(
				new String(Files.readAllBytes(this.noSolutionRequestResource.getFile().toPath())),
				LocationRequest.class);
		assertThrows(TwoDimensionalTrilaterationException.class, () -> {
			locationController.locate(noSolutionRequestData);
		});
	}

	@Test
	void testSolutionRequest() throws Exception {
		LocationRequest validSolutionRequestData = objectMapper.readValue(
				new String(Files.readAllBytes(this.validSolutionRequestResource.getFile().toPath())),
				LocationRequest.class);
		assertTrue(Point2DComparator.AreEquals(new Point2D(-200, 0),
				locationController.locate(validSolutionRequestData).getLocation()));
	}
}
