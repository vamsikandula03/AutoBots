package layers.serviceLayer;

import exceptions.CredentialsException;
import exceptions.NotAuthorizedException;
import interfaces.ServiceLayer;
import models.MeetingRoom;
import models.MeetingRoomRequest;
import models.User;
import utils.MeetingRoomRequestStatus;
import utils.MeetingRoomType;
import utils.Roles;
import java.io.File;
import java.util.List;

public class ServiceLayerProxyImpl implements ServiceLayer {
    ServiceLayer serviceLayer= new ServiceLayerImpl();
    User user;
    public ServiceLayerProxyImpl(User user) {
this.user = user;
    }
    @Override
    public User Login(int userid, String password) {
        User u1= null;
        try {
            u1 = serviceLayer.Login(userid, password);
        } catch (CredentialsException e) {
            throw new RuntimeException(e);
        }
        return u1;

    }

    @Override
    public List<MeetingRoom> getAvailableMeetingRooms() {
        if(user.getRole()==Roles.MANAGER){
            return serviceLayer.getAvailableMeetingRooms();
        }
        throw new NotAuthorizedException("Not authorized");

    }

    @Override
    public String requestForMeetingRoom(MeetingRoomRequest request){
        if(user.getRole()== Roles.MANAGER){
            return serviceLayer.requestForMeetingRoom(request);
        }
        throw new NotAuthorizedException("Not authorized");
    }

    @Override
    public List<MeetingRoom> getScheduledMeetingRooms(int empid) {
        if(user.getRole()==Roles.MANAGER || user.getRole()==Roles.EMPLOYEE){
            return serviceLayer.getScheduledMeetingRooms(empid);

        }
        throw new NotAuthorizedException("Not authorized");
    }

    @Override
    public boolean addUsersToScheduledMeetingRoom(int empid, int requestid, List<User> users) {
        if(user.getRole()==Roles.MANAGER){
            return serviceLayer.addUsersToScheduledMeetingRoom(empid, requestid, users);
        }
        throw new NotAuthorizedException("Not authorized");
    }

    @Override
    public boolean removeUsersFromScheduledMeetingRoom(int empid, int requestid, List<User> users) {
        if(user.getRole()==Roles.MANAGER){
            return serviceLayer.removeUsersFromScheduledMeetingRoom(empid, requestid, users);
        }
        throw new NotAuthorizedException("Not authorized");
    }

    @Override
    public List<MeetingRoom> getMeetingRoomsWithType(MeetingRoomType type) {
        if(user.getRole()==Roles.MANAGER){
            return serviceLayer.getMeetingRoomsWithType(type);
        }
        throw new NotAuthorizedException("Not authorized");
    }

    @Override
    public List<MeetingRoom> getAllMeetingRooms() {
        if(user.getRole()==Roles.ADMIN || user.getRole()==Roles.MANAGER){
            return serviceLayer.getAllMeetingRooms();
        }
        throw new NotAuthorizedException("Not authorized");
    }

    @Override
    public MeetingRoom addMeetingRoom(MeetingRoom room) {
        if(user.getRole()==Roles.ADMIN){
            return serviceLayer.addMeetingRoom(room);
        }
        throw new NotAuthorizedException("Not authorized");

    }

    @Override
    public MeetingRoom updateMeetingRoom(int roomid) {
        if(user.getRole()==Roles.ADMIN){
            return serviceLayer.updateMeetingRoom(roomid);
        }
        throw new NotAuthorizedException("Not authorized");
    }

    @Override
    public MeetingRoom deleteMeetingRoom(int roomid) {
        if(user.getRole()==Roles.ADMIN){
            return serviceLayer.deleteMeetingRoom(roomid);
        }
        throw new NotAuthorizedException("Not authorized");
    }

    @Override
    public String updateMeetingRoomRequest(int reqid, MeetingRoomRequestStatus status) {
        if(user.getRole()==Roles.ADMIN){
            return serviceLayer.updateMeetingRoomRequest(reqid, status);
        }
        throw new NotAuthorizedException("Not authorized");
    }

    @Override
    public String importUsers(File file) {
        if(user.getRole()==Roles.ADMIN){
            return serviceLayer.importUsers(file);
        }
        throw new NotAuthorizedException("Not authorized");
    }

    @Override
    public List<MeetingRoomRequest> getAllMeetingRoomRequests() {
        if(user.getRole()== Roles.ADMIN){
            return  serviceLayer.getAllMeetingRoomRequests();
        }
        throw new NotAuthorizedException("not authorized");
    }

    @Override
    public boolean checkCredits(int empid, int roomid) {
        if(user.getRole()==Roles.MANAGER){
        return serviceLayer.checkCredits(empid, roomid);}
        throw new NotAuthorizedException("Not authorized");
    }



    @Override
    public boolean updateCredits() {
        return serviceLayer.updateCredits();
    }
}