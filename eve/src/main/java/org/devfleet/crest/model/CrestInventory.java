package org.devfleet.crest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

class CrestInventory extends CrestEntity {

    @JsonProperty
    private int flag;

    @JsonProperty
    private long quantity;

    @JsonProperty
    private CrestItem item;

    public int getFlag() {
        return flag;
    }

    public long getQuantity() {
        return quantity;
    }

    public CrestItem getItem() {
        return item;
    }
}
