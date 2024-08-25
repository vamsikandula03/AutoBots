package models;

import utils.Amenity;

import java.util.List;



public class MeetingRoom {
    private int roomId;
    private String name;
    private int basicCost;
    private int seatingCapacity;
    private List<Amenity> amenities;
    private  boolean isAvailable = false;

    public MeetingRoom(int roomId,String name, int basicCost, int seatingCapacity, List<Amenity> amenities, boolean isAvailable) {
        this.roomId = roomId;
        this.name=name;
        this.basicCost=basicCost;
        this.seatingCapacity=seatingCapacity;
        this.amenities = amenities;
        this.isAvailable = isAvailable;
    }

    public int getBasicCost() {
        return basicCost;
    }

    public void setBasicCost(int basicCost) {
        this.basicCost = basicCost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }


    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public double calculateCost() {
        return amenities.stream().mapToDouble(Amenity::getTotalCost).sum();
    }
}
