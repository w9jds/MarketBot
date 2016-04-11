package core.eve.crest;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class CrestPosition {

    @JsonProperty
    private double x;

    @JsonProperty
    private double y;

    @JsonProperty
    private double z;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
