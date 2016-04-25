package org.devfleet.crest;

import java.util.List;
import org.devfleet.crest.model.CrestCharacter;
import org.devfleet.crest.model.CrestContact;
import org.devfleet.crest.model.CrestServerStatus;
import org.devfleet.crest.model.CrestSolarSystem;
import org.devfleet.crest.model.CrestFitting;
import org.devfleet.crest.model.CrestWaypoint;

public interface CrestService {

    CrestServerStatus getServerStatus();

    CrestCharacter getCharacter();

    CrestSolarSystem getLocation();

    CrestSolarSystem getLocation(long solarSystemId);

    List<CrestSolarSystem> getLocations();

    List<CrestContact> getContacts();

    CrestContact getContact(final long contactID);

    boolean addContact(final CrestContact contact);

    boolean deleteContact(final long contactID);

    List<CrestFitting> getFittings();

    boolean addFitting(final CrestFitting fitting);

    boolean deleteFitting(final long fittingID);

    boolean addWaypoints(final List<CrestWaypoint> waypoints);

    boolean setWaypoints(final List<CrestWaypoint> waypoints);
}
