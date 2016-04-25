package org.devfleet.crest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

//https://login.eveonline.com/oauth/verify
//@see https://eveonline-third-party-documentation.readthedocs.org/en/latest/sso/obtaincharacterid/
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrestCharacterStatus {

    @JsonProperty("CharacterName")
    private String characterName;

    @JsonProperty("CharacterID")
    private long characterID;

    @JsonProperty("TokenType")
    private String tokenType;

    @JsonProperty("Scopes")
    private String scopes;

    @JsonProperty("CharacterOwnerHash")
    private String hash;

    public String getCharacterName() {
        return characterName;
    }

    public long getCharacterID() {
        return characterID;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getScopes() {
        return scopes;
    }

    public String getHash() {
        return hash;
    }
}
