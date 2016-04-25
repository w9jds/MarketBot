package org.devfleet.crest.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CrestMarketGroup extends CrestItem {

    private String type;
    private String parent;

    @JsonProperty("types")
    public void setType(List<String> types) {
        this.type = types.get(0);
    }

    @JsonProperty("parentGroup")
    public void setParent(List<String> parents) {
        this.parent = parents.get(0);
    }

    public String getType() {
        return type;
    }

    public String getParent() {
        return parent;
    }
}
