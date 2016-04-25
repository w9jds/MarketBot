package org.devfleet.crest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class CrestToken extends CrestEntity {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    //@JsonProperty("expires_in")
   // private long expiresIn; //just don't use this

    @JsonProperty("refresh_token")
    private String refreshToken;

    private final long created = System.currentTimeMillis();

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    /*public long getExpiresIn() {
        return expiresIn;
    }*/

    public String getRefreshToken() {
        return refreshToken;
    }

    private static final long MN15 = 15l * 60000l;//SSO is 20mn
    public final long expiresOn() {
        return this.created + MN15;
    }
}
