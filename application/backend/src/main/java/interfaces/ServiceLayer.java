package interfaces;
import exceptions.CredentialsException;
import models.MeetingRoom;
import models.MeetingRoomRequest;
import models.User;
import utils.MeetingRoomRequestStatus;
import utils.MeetingRoomType;

import java.io.File;
import java.util.List;

public interface ServiceLayer {
//TODO:user service
    User Login(int userid,String password) throws CredentialsException;

// TODO:managerService
    List<MeetingRoom> getAvailableMeetingRooms();
    String requestForMeetingRoom(MeetingRoomRequest request);
    List<MeetingRoom> getScheduledMeetingRooms(int empid);
    boolean addUsersToScheduledMeetingRoom(int empid,int requestid,List<User> users);
    boolean removeUsersFromScheduledMeetingRoom(int empid,int requestid,List<User> users);
    List<MeetingRoom> getMeetingRoomsWithType(MeetingRoomType type);


// TODO:adminService
    List<MeetingRoom> getAllMeetingRooms();
    MeetingRoom addMeetingRoom(MeetingRoom room);
    MeetingRoom updateMeetingRoom(int roomid);
    MeetingRoom deleteMeetingRoom(int roomid);
    String updateMeetingRoomRequest(int reqid, MeetingRoomRequestStatus status);
    String importUsers(File file);
   List<MeetingRoomRequest> getAllMeetingRoomRequests();


// TODO: utils
    boolean checkCredits(int empid,int roomid);
    boolean updateCredits();


}
