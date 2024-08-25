package utils;

public enum MeetingRoomRequestStatus {
    REQUESTED("Requested"),
    APPROVED("Approved"),
    REJECTED("Rejected");
    private final String value;
    MeetingRoomRequestStatus( String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
