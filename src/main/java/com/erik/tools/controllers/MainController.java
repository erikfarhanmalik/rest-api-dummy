package com.erik.tools.controllers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Controller
public class MainController {

	@RequestMapping("/api/{json-filename}")
	@CrossOrigin(origins = "*")
	public ResponseEntity<ObjectNode> index(@PathVariable("json-filename") String jsonFileName) {
		String folder = System.getProperty("dummyapi.folder");
		return getJsonResponse(folder, jsonFileName);
	}

	private ResponseEntity<ObjectNode> getJsonResponse(String folder, String jsonFileName) {
		ResponseEntity<ObjectNode> response;
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		try {
			String jsonString = "{}";
			if (folder != null) {
				String path = folder + jsonFileName + ".json";
				System.out.println("Json files location: " + path);
				jsonString = readFile(path);
			} else {
				String path = "jsons/" + jsonFileName + ".json";
				System.out.println("Json files location: " + path);
				InputStream stream = MainController.class.getClassLoader().getResourceAsStream(path);
				StringBuilder textBuilder = new StringBuilder();
				try (Reader reader = new BufferedReader(
						new InputStreamReader(stream, Charset.forName(StandardCharsets.UTF_8.name())))) {
					int c = 0;
					while ((c = reader.read()) != -1) {
						textBuilder.append((char) c);
					}
				}
				jsonString = textBuilder.toString();
			}
			node = mapper.readValue(jsonString, ObjectNode.class);
		} catch (FileNotFoundException e) {
			node.put("message", "Json not found");
		} catch (IOException e) {
			node.put("message", "Request throw IO Exception");
		}
		
		int responseCode = getResponseCode(jsonFileName);
		if(responseCode == 200) {			
			response = ResponseEntity.ok(node);
		} else {
			response = ResponseEntity.status(responseCode).body(node);
		}
		return response;
	}

	private String readFile(String path) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded);
	}

	private int getResponseCode(String filename) {
		int code = 200;

		if (filename.matches("^(.*)?-\\d{3}$")) {
			String[] split = filename.split("-");
			code = Integer.valueOf(split[split.length-1]);
		}

		return code;
	}
}
