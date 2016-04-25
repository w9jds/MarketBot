package org.devfleet.crest.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

class CrestConstellation extends CrestEntity {

    @JsonProperty
    private CrestPosition position;

    @JsonProperty
    private String name;

    @JsonProperty
    private List<CrestItem> systems;

    @JsonProperty("region")
    @JsonDeserialize(using = RefDeserializer.class)
    private String regionRef;

    public CrestPosition getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public List<CrestItem> getSystems() {
        return systems;
    }

    public String getRegionRef() {
        return regionRef;
    }
}
