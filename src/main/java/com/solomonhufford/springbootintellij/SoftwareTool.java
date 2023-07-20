package com.solomonhufford.springbootintellij;

import java.util.UUID;

// Domain Class "Software Tools", will be used as a model for the rest of them.
public class SoftwareTool {
    private final String id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }
}
