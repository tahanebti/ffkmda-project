package com.tahanebti.ffkmda.role;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum RoleName {
    ROLE_ADMIN("ADMIN"),
    ROLE_USER("USER");
    
    @JsonProperty("label")
    String label;

    RoleName(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
    
    
    public static RoleName fromString(String text) {
        for (RoleName b : RoleName.values()) {
            if (b.label.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}

