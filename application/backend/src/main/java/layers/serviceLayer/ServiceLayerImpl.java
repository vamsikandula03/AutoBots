package layers.serviceLayer;

import exceptions.CredentialsException;
import interfaces.DAOLayer;
import interfaces.ServiceLayer;
import layers.DAOLayer.DAOFactory;
import models.MeetingRoom;
import models.MeetingRoomRequest;
import models.MeetingRoomTypeRequired;
import models.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import utils.Amenity;
import utils.MeetingRoomRequestStatus;
import utils.MeetingRoomType;
import utils.Roles;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceLayerImpl implements ServiceLayer {
    DAOLayer dao = DAOFactory.getDAO();

    public void setDao(DAOLayer dao) {
        this.dao = dao;
    }

    @Override
    public User Login(int userid, String password) throws CredentialsException {
        String userPassword = dao.getPassword(userid);
        if (password.equals(userPassword)) {
            return dao.getUserById(userid);
        }
        throw new CredentialsException("Please Enter Correct Credentials");
    }

    @Override
    public List<MeetingRoom> getAvailableMeetingRooms() {
        List<MeetingRoom> rooms = dao.getMeetingRooms();
        return rooms.stream().filter(MeetingRoom::isAvailable).collect(Collectors.toList());
    }

    @Override
    public String requestForMeetingRoom(MeetingRoomRequest request) {
        MeetingRoom room = dao.getMeetingRoomById(request.getRoomId());
        User manager = dao.getUserById(request.getManagerId());

        if (room != null && room.isAvailable()) {
            // Check if the manager has enough credits
            if (!checkCredits(manager.getEmpid(), room.getRoomId())) {
                return "Manager does not have enough credits to book the room.";
            }

            List<MeetingRoomRequest> existingRequests = dao.getMeetingRoomRequests();

            boolean isTimelineAvailable = existingRequests.stream()
                    .noneMatch(existingRequest ->
                            existingRequest.getRoomId() == request.getRoomId() &&
                                    existingRequest.getDate().equals(request.getDate()) &&
                                    ((existingRequest.getStartTime().before(request.getEndTime()) &&
                                            existingRequest.getEndTime().after(request.getStartTime())) ||
                                            (existingRequest.getStartTime().equals(request.getStartTime()) ||
                                                    existingRequest.getEndTime().equals(request.getEndTime())))
                    );

            if (isTimelineAvailable) {
                request.setStatus(MeetingRoomRequestStatus.REQUESTED);
                dao.addMeetingRoomRequest(request);
                return "Meeting room request submitted successfully";
            } else {
                return "Meeting room is already booked for the requested time slot.";
            }
        }
        return "Meeting room not available";
    }


    @Override
    public List<MeetingRoom> getScheduledMeetingRooms(int empid) {
        List<MeetingRoomRequest> userRequests = dao.getMeetingRoomRequests().stream()
                .filter(request -> request.getManagerId() == empid ||
                        request.getUserList().stream().anyMatch(user -> user.getEmpid() == empid))
                .collect(Collectors.toList());

        return userRequests.stream()
                .map(request -> dao.getMeetingRoomById(request.getRoomId()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean addUsersToScheduledMeetingRoom(int empid, int requestid, List<User> users) {
        MeetingRoomRequest request = dao.getMeetingRoomRequestById(requestid);
        if (request != null && request.getManagerId() == empid) {
            request.getUserList().addAll(users);
            dao.updateMeetingRoomRequest(request);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeUsersFromScheduledMeetingRoom(int empid, int requestid, List<User> users) {
        MeetingRoomRequest request = dao.getMeetingRoomRequestById(requestid);
        if (request != null && request.getManagerId() == empid) {
            request.getUserList().removeAll(users);
            dao.updateMeetingRoomRequest(request);
            return true;
        }
        return false;
    }

    @Override
    public List<MeetingRoom> getMeetingRoomsWithType(MeetingRoomType type) {
        MeetingRoomTypeRequired requiredType = dao.getMeetingRoomTypeByType(type);
        List<Amenity> requiredAmenities = requiredType.getAmenityList();
        List<MeetingRoom> allRooms = dao.getMeetingRooms();
        List<MeetingRoom> matchingRooms = new ArrayList<>();
        for (MeetingRoom room : allRooms) {
            List<Amenity> roomAmenities = room.getAmenities();
            boolean allAmenitiesPresent = true;
            for (Amenity requiredAmenity : requiredAmenities) {
                boolean found = false;
                for (Amenity roomAmenity : roomAmenities) {
                    if (roomAmenity.getType().equals(requiredAmenity.getType()) &&
                            roomAmenity.getCount() >= requiredAmenity.getCount()) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    allAmenitiesPresent = false;
                    break;
                }
            }
            if (allAmenitiesPresent) {
                matchingRooms.add(room);
            }
        }

        return matchingRooms;
    }

    @Override
    public List<MeetingRoom> getAllMeetingRooms() {
        return dao.getMeetingRooms();
    }

    @Override
    public MeetingRoom addMeetingRoom(MeetingRoom room) {
        return dao.addMeetingRoom(room);
    }

    @Override
    public MeetingRoom updateMeetingRoom(int roomid) {
        MeetingRoom room = dao.getMeetingRoomById(roomid);
        if (room != null) {
            // Assuming some logic to update the meeting room properties
            dao.updateMeetingRoom(room);
        }
        return room;
    }

    @Override
    public MeetingRoom deleteMeetingRoom(int roomid) {
        return dao.deleteMeetingRoomById(roomid);
    }

    @Override
    public String updateMeetingRoomRequest(int reqid, MeetingRoomRequestStatus status) {
        MeetingRoomRequest request = dao.getMeetingRoomRequestById(reqid);
        if (request != null) {
            request.setStatus(status);
            dao.updateMeetingRoomRequest(request);
            return "Meeting room request status updated successfully";
        }
        return "Meeting room request not found";
    }

    @Override
    public String importUsers(File file) {
        return readXmlData(file);
    }

    @Override
    public List<MeetingRoomRequest> getAllMeetingRoomRequests() {
        return  dao.getMeetingRoomRequests();
    }


    @Override
    public boolean checkCredits(int empid, int roomid) {
        User user = dao.getUserById(empid);
        MeetingRoom room = dao.getMeetingRoomById(roomid);
        if (user != null && room != null) {
            double roomCost = room.calculateCost();
            return user.getCredits() >= roomCost;
        }
        return false;
    }

    @Override
    public boolean updateCredits() {
        List<User> users=dao.getUsers();
        List<User>managers=  users.stream().filter(user -> user.getRole()== Roles.MANAGER).collect(Collectors.toList());
        managers.forEach(manager->manager.setCredits(2000));
        managers.forEach(manager->dao.updateUser(manager));
        return true;
    }
    public String readXmlData(File file) {
        int rowCount = 0;

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList usersList = doc.getElementsByTagName("user");

            for (int itr = 0; itr < usersList.getLength(); itr++) {
                Node user = usersList.item(itr);
                if (user.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) user;
                    int userId=Integer.parseInt(element.getElementsByTagName("userId").item(0).getTextContent());
                    String username=element.getElementsByTagName("userName").item(0).getTextContent();
                            String role=element.getElementsByTagName("role").item(0).getTextContent();
                           String email= element.getElementsByTagName("email").item(0).getTextContent();
                        String phone=    element.getElementsByTagName("phone").item(0).getTextContent();
                        Roles roleenum=switch (role){
                            case "admin" -> Roles.ADMIN;
                            case "manager" -> Roles.MANAGER;
                            case "employee" -> Roles.EMPLOYEE;
                            default -> throw new IllegalStateException("Unexpected value: " + role);
                        };
                    User newUser = new User(userId,username,phone,email,roleenum);
                    User addedUser=dao.addUser(newUser);
                    if (addedUser != null) {
                        rowCount++;
                    }
                }
            }
            return rowCount + " rows added";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
