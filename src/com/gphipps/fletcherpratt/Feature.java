package com.gphipps.fletcherpratt;

public abstract class Feature {
    
    private String description;
    public Feature( String description) {
        super();
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public abstract double getPoints();
}
