package core.eve.crest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CrestDogmaAttribute extends CrestItem {

    @JsonProperty
    private String displayName;

    @JsonProperty
    private Double defaultValue;

    @JsonProperty("value")
    private Double currentValue;

    @JsonProperty
    private Boolean highIsGood;

    @JsonProperty
    private Boolean stackable;

    public String getDisplayName() {
        return displayName;
    }

    public Double getDefaultValue() {
        return defaultValue;
    }

    public Boolean getHighIsGood() {
        return highIsGood;
    }

    public Boolean getStackable() {
        return stackable;
    }

    public final Double getCurrentValue() {
        return currentValue;
    }

    public final void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }
}
