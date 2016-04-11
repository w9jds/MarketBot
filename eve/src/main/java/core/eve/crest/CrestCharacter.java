package core.eve.crest;

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
    private String accessToken;

    public boolean getNPC() {
        return isNPC;
    }

    public CrestCorporation getCorporation() {
        return corporation;
    }

    public String getCapsuleerRef() {
        return capsuleerRef;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
