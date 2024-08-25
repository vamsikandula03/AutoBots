package interfaces;

import models.MeetingRoom;
import models.MeetingRoomRequest;
import models.MeetingRoomTypeRequired;
import models.User;
import utils.MeetingRoomType;

import java.util.List;

public interface DAOLayer {
    //TODO:users dao
    List<User> getUsers();
    User getUserById(int id);
    User addUser(User user);
    User updateUser(User user);
    User deleteUserById(int id);
    //TODO:meetingRoom dao
    List<MeetingRoom> getMeetingRooms();
    MeetingRoom getMeetingRoomById(int id);
    MeetingRoom addMeetingRoom(MeetingRoom meetingRoom);
    MeetingRoom updateMeetingRoom(MeetingRoom meetingRoom);
    MeetingRoom deleteMeetingRoomById(int id);
    // TODO:RequestRoom dao
    List<MeetingRoomRequest> getMeetingRoomRequests();
    MeetingRoomRequest getMeetingRoomRequestById(int id);
    MeetingRoomRequest addMeetingRoomRequest(MeetingRoomRequest meetingRoomRequest);
    MeetingRoomRequest updateMeetingRoomRequest(MeetingRoomRequest meetingRoomRequest);
    MeetingRoomRequest deleteMeetingRoomRequestById(int id);
    List<MeetingRoomRequest> getMeetingRoomRequestsByManagerId(int managerId);
    //TODO authentication:
    String getPassword(int empid);

    //TODO: Types:
    List<MeetingRoomTypeRequired> getMeetingRoomTypesWithRequiredAmenities();
    MeetingRoomTypeRequired getMeetingRoomTypeByType(MeetingRoomType type);





}
