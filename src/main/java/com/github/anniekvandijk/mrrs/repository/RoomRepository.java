package com.github.anniekvandijk.mrrs.repository;

import com.github.anniekvandijk.mrrs.domain.MeetingRoom;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RoomRepository {
    private Map<String, MeetingRoom> roomsByLocation = new HashMap<>();
    private Map<String, MeetingRoom> roomsByName = new HashMap<>();


    public void add(final MeetingRoom meetingRoom) {
        if (roomsByLocation.containsKey(meetingRoom.getLocation())) {
            throw new IllegalArgumentException("Argument 'location' with value '" + meetingRoom.getLocation()
                    + "' is an already known MeetingRoom");
        }
        roomsByLocation.put(meetingRoom.getLocation(), meetingRoom);

        if (roomsByName.containsKey(meetingRoom.getName())) {
            throw new IllegalArgumentException("Argument 'name' with value '" + meetingRoom.getName()
                    + "' is an already known MeetingRoom");
        }
        roomsByName.put(meetingRoom.getName(), meetingRoom);
    }

    public MeetingRoom search(String text) {
        if ((! roomsByLocation.containsKey(text)) && (! roomsByName.containsKey(text))) {
            throw new IllegalArgumentException("Argument 'text' with value '" + text
                    + "' is not a known MeetingRoom");
        }

        if (roomsByLocation.containsKey(text)) {
            return roomsByLocation.get(text);
        }
        return roomsByName.get(text);
    }

    public int size() {
        return roomsByLocation.size();
    }

    public Set<String> getLocations() {
        return roomsByLocation.keySet();
    }

    public MeetingRoom getByLocation(final String location) {
        final String locationCln = StringUtils.trimToNull(location);
        if (locationCln == null) {
            throw new IllegalArgumentException("Argument 'location' should not be null.");
        }
        if (! roomsByLocation.containsKey(locationCln)) {
            throw new IllegalArgumentException("Argument 'location' with value '" + locationCln + "' not a known location.");
        }

        return roomsByLocation.get(location);
    }
}
