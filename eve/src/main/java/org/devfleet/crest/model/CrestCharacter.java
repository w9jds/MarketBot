package org.devfleet.crest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class CrestCharacter extends CrestItem {

    @JsonProperty
    private boolean isNPC;

    //FIXME maybe portraits

    @JsonProperty
    private CrestCorporation corporation;

    @JsonProperty("capsuleer")
    @JsonDeserialize(using = RefDeserializer.class)
    private String capsuleerRef;

    @JsonIgnore
    private String refreshToken;

    public boolean getNPC() {
        return isNPC;
    }

    public CrestCorporation getCorporation() {
        return corporation;
    }

    public String getCapsuleerRef() {
        return capsuleerRef;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
