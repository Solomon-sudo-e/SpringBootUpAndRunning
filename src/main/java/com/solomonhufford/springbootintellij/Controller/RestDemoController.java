package com.solomonhufford.springbootintellij.Controller;

import com.solomonhufford.springbootintellij.SoftwareTool;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tools")
public class RestDemoController {
    private List<SoftwareTool> tools = new ArrayList<>();

    public RestDemoController() {
        tools.addAll(List.of(
                new SoftwareTool("Kafka"),
                new SoftwareTool("SpringBoot"),
                new SoftwareTool("TensorFlow"),
                new SoftwareTool("SciKit")
        ));
    }

    @GetMapping
    Iterable<SoftwareTool> getTools() {
        return tools;
    }

    @GetMapping("/{id}")
    Optional<SoftwareTool> getToolById(@PathVariable String id) {
        for (SoftwareTool tool : tools) {
            if (tool.getId().equals(id)) {
                return Optional.of(tool);
            }
        }
        return Optional.empty();
    }

    @PostMapping
    SoftwareTool postTool(@RequestBody SoftwareTool tool) {
        tools.add(tool);
        return tool;
    }

    @PutMapping("/{id}")
    ResponseEntity<SoftwareTool> putTool(@PathVariable String id, @RequestBody SoftwareTool tool) {
        int toolIndex = -1;
        for (SoftwareTool t : tools) {
            toolIndex = tools.indexOf(t);
            tools.set(toolIndex, tool);
        }
        return (toolIndex == -1) ?
                new ResponseEntity<>(postTool(tool), HttpStatus.CREATED) :
                new ResponseEntity<>(tool, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    void deleteTool(@PathVariable String id) {
        tools.removeIf(tool -> tool.getId().equals(id));
    }

    @DeleteMapping("/{name}")
    void deleteToolByName(@PathVariable String name) {
        tools.removeIf(tool -> tool.getName().equals(name));
    }
}
