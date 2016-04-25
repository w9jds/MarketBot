package org.devfleet.crest.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CrestFitting extends CrestItem {

    @JsonProperty
    private CrestItem ship;

    @JsonProperty("items")
    private List<CrestInventory> inventory;

    @JsonProperty
    public final long getFittingID() {
        return super.getId();
    }

    @JsonProperty
    public final void setFittingID(final long id) {
        super.setId(id);
    }

    public CrestItem getShip() {
        return ship;
    }

    public List<CrestInventory> getInventory() {
        return inventory;
    }
}
