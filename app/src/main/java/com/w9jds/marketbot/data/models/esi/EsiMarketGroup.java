package com.w9jds.marketbot.data.models.esi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EsiMarketGroup {

    @JsonProperty
    String description;

    @JsonProperty("market_group_id")
    int groupId;

    @JsonProperty
    String name;

    @JsonProperty("parent_group_id")
    int parentGroupId;

    @JsonProperty
    int[] types;

    public int getGroupId() {
        return groupId;
    }

    public int getParentGroupId() {
        return parentGroupId;
    }

    public int[] getTypes() {
        return types;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}
