package com.solomonhufford.springbootintellij;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class SpringBootIntellijApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootIntellijApplication.class, args);
	}

}

@Component
class DataLoader {
	private final SoftwareToolRepository softwareToolRepository;

	public DataLoader(SoftwareToolRepository softwareToolRepository) {
		this.softwareToolRepository = softwareToolRepository;
	}

	@PostConstruct
	private void loadData() {
		softwareToolRepository.saveAll(List.of(
				new SoftwareTool("Kafka"),
				new SoftwareTool("SpringBoot"),
				new SoftwareTool("TensorFlow"),
				new SoftwareTool("SciKit")
		));
	}
}

@RestController
@RequestMapping("/tools")
class RestDemoController {

	private final SoftwareToolRepository softwareToolRepository;

	public RestDemoController(SoftwareToolRepository softwareToolRepository) {
		this.softwareToolRepository = softwareToolRepository;
	}

	@GetMapping
	Iterable<SoftwareTool> getTools() {
		return softwareToolRepository.findAll();
	}

	@GetMapping("/{id}")
	Optional<SoftwareTool> getToolById(@PathVariable String id) {
		return softwareToolRepository.findById(id);
	}

	@PostMapping
	SoftwareTool postTool(@RequestBody SoftwareTool tool) {
		return softwareToolRepository.save(tool);
	}

	@PutMapping("/{id}")
	ResponseEntity<SoftwareTool> putTool(@PathVariable String id, @RequestBody SoftwareTool tool) {
		return (!softwareToolRepository.existsById(id))
				? new ResponseEntity<>(softwareToolRepository.save(tool), HttpStatus.CREATED)
				: new ResponseEntity<>(softwareToolRepository.save(tool), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	void deleteTool(@PathVariable String id) {
		softwareToolRepository.deleteById(id);
	}
}

interface SoftwareToolRepository extends CrudRepository<SoftwareTool, String> {}

// Domain Class "Software Tools", will be used as a model for the rest of them.
@Entity
class SoftwareTool {
	@Id
	private String id;
	private String name;

	// Builds the model of "Software Tools"
	public SoftwareTool(String id, String name) {
		this.id = id;
		this.name = name;
	}

	// Makes a unique Identifier if there is no identifier offered at the time it was assigned.
	public SoftwareTool(String name) {
		this(UUID.randomUUID().toString(), name);
	}

	public SoftwareTool() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}