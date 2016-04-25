package org.devfleet.crest.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

class CrestAlliance extends CrestEntity {

    @JsonProperty
    private String url;

    @JsonProperty
    private String shortName;

    @JsonProperty
    private String description;

    @JsonProperty
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private long startDate;

    @JsonProperty
    private boolean deleted;

    @JsonProperty
    private CrestCharacter creatorCharacter;

    @JsonProperty
    private CrestCorporation executorCorporation;

    @JsonProperty
    private CrestCorporation creatorCorporation;

    @JsonProperty
    private List<CrestCorporation> corporations;

    public String getUrl() {
        return url;
    }

    public String getShortName() {
        return shortName;
    }

    public String getDescription() {
        return description;
    }

    public long getStartDate() {
        return startDate;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public CrestCharacter getCreatorCharacter() {
        return creatorCharacter;
    }

    public CrestCorporation getExecutorCorporation() {
        return executorCorporation;
    }

    public CrestCorporation getCreatorCorporation() {
        return creatorCorporation;
    }

    public List<CrestCorporation> getCorporations() {
        return corporations;
    }
}
