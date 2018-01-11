package com.erik.tools.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class MainController {

	@RequestMapping("/api/{json-filename}")
	@CrossOrigin(origins = "*")
	public ObjectNode index(@PathVariable("json-filename") String jsonFileName) {
		String folder = System.getProperty("dummyapi.folder");
		String path = folder + jsonFileName + ".json";
		System.out.println("Json files location: " + path);
		return getJsonResponse(path);
	}

	private ObjectNode getJsonResponse(String path) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		try {
			String jsonString = readFile(path);
			node = mapper.readValue(jsonString, ObjectNode.class);
		} catch (FileNotFoundException e) {
			node.put("message", path + " not found");
		} catch (IOException e) {
			node.put("message", path + " throw IO Exception");
		}
		return node;
	}
	
	private String readFile(String path) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded);
	}
}
