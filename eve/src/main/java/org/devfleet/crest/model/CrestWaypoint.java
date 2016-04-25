package org.devfleet.crest.model;

public class CrestWaypoint extends CrestEntity {

    private boolean clearOtherWaypoints = false;
    private boolean first = false;

    //you only need href and id to POST one
    private CrestItem solarSystem;

    public boolean getClearOtherWaypoints() {
        return clearOtherWaypoints;
    }

    public void setClearOtherWaypoints(boolean clearOtherWaypoints) {
        this.clearOtherWaypoints = clearOtherWaypoints;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public CrestItem getSolarSystem() {
        return solarSystem;
    }

    public void setSolarSystem(CrestItem solarSystem) {
        this.solarSystem = solarSystem;
    }
}
