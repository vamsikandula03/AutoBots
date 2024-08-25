package utils;

public enum MeetingRoomType {
    ClASSROOM_TRAINING("Classroom Training"),
    ONLINE_TRAINING("Online Training"),
    CONFERENCE_CALL("Conference Call"),
    BUSINESS("Business"),
    ;

    private final String value;

    public String getValue() {
        return value;
    }

    MeetingRoomType(String value) {
        this.value = value;
    }
}
