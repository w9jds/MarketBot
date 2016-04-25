package org.devfleet.crest.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class CrestSolarSystem extends CrestItem {

    @JsonProperty("stats")
    @JsonDeserialize(using = RefDeserializer.class)
    private String statsRef;

    @JsonProperty("planets")
    @JsonDeserialize(using = RefDeserializer.class)
    private List<String> planetsRef;

    @JsonProperty
    private double securityStatus;

    @JsonProperty
    private String securityClass;

    @JsonProperty
    private CrestPosition position;

    @JsonProperty
    private CrestItem sovereignty;

    @JsonProperty
    private CrestItem constellation;

    public String getStatRef() {
        return statsRef;
    }

    public List<String> getPlanetRefs() {
        return planetsRef;
    }

    public double getSecurityStatus() {
        return securityStatus;
    }

    public String getSecurityClass() {
        return securityClass;
    }

    public CrestPosition getPosition() {
        return position;
    }

    public CrestItem getSovereignty() {
        return sovereignty;
    }

    public CrestItem getConstellation() {
        return constellation;
    }
}
