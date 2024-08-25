package models;

import utils.Amenity;
import utils.MeetingRoomType;

import java.util.List;

public class MeetingRoomTypeRequired {
    MeetingRoomType type;
    List<Amenity> amenityList;

    public MeetingRoomTypeRequired(MeetingRoomType type, List<Amenity> amenityList) {
        this.type = type;
        this.amenityList = amenityList;
    }

    public MeetingRoomType getType() {
        return type;
    }

    public void setType(MeetingRoomType type) {
        this.type = type;
    }

    public List<Amenity> getAmenityList() {
        return amenityList;
    }

    public void setAmenityList(List<Amenity> amenityList) {
        this.amenityList = amenityList;
    }
}
