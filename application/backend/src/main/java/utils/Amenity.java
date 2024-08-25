package utils;

public enum Amenity {
    // Define amenities with a single seating category
    SEATING_CAPACITY(2),  // Example: each seat could cost 2 units
    PROJECTOR(5), WIFI(10), CONFERENCE_CALL(15),
    WHITEBOARD(5), WATER_DISPENSER(5), TV(10), COFFEE_MACHINE(10);

    private final int costPerUnit;
    private int count;

    Amenity(int costPerUnit) {
        this.costPerUnit = costPerUnit;
        this.count = 0;  // Initialize count to 0
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCostPerUnit() {
        return costPerUnit;
    }

    public int getTotalCost() {
        return count * costPerUnit;
    }

    public String getType() {
        return this.name();
    }

    // Example method to add more amenities with specified count
    public void addCount(int additionalCount) {
        this.count += additionalCount;
    }
}
