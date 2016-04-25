package org.devfleet.crest.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class CrestServerStatus extends CrestEntity {

    @JsonProperty
    private String serverName;

    @JsonProperty
    private String serverVersion;

    @JsonProperty
    private Map<String, Integer> userCounts = new HashMap<>();

    @JsonProperty
    private Map<String, String> serviceStatus = new HashMap<>();

    public String getServerName() {
        return serverName;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public int getDustCount() {
        return getInt("dust");
    }

    public int getEveCount() {
        return getInt("eve");
    }

    public boolean getDustOnline() {
        return getOnline("dust");
    }

    public boolean getEveOnline() {
        return getOnline("eve");
    }

    public boolean getServerOnline() {
        return getOnline("server");
    }

    private int getInt(final String key) {
        final Integer a = userCounts.get(key);
        return (null == a) ? 0 : a;
    }

    private boolean getOnline(final String key) {
        final String s = serviceStatus.get(key);
        return s.equalsIgnoreCase("online");
    }
}
