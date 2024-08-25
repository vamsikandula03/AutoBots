package layers.DAOLayer;

import exceptions.ResourceAlreadyExistsException;
import exceptions.ResourceNotFoundException;
import interfaces.DAOLayer;
import models.MeetingRoom;
import models.MeetingRoomRequest;
import models.MeetingRoomTypeRequired;
import models.User;
import utils.Amenity;
import utils.MeetingRoomType;
import utils.Roles;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class DaoLayerImpl implements DAOLayer {
    static Connection con = null;
    static PreparedStatement pst = null;
    static ResultSet rs = null;
    static Statement stmt = null;


    private Optional<Connection> getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/codefury", "root", "root");
            return Optional.of(con);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        Optional<Connection> opConnection = getConnection();
        if (opConnection.isPresent()) {
            try (Connection con = opConnection.get();
                 Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
                while (rs.next()) {
                    int id = rs.getInt("userId");
                    String name = rs.getString("name");
                    String role = rs.getString("role");
                    String phone = rs.getString("phone");
                    String email = rs.getString("email");
                    Roles roleenum=switch (role){
                        case "admin" -> Roles.ADMIN;
                        case "manager" -> Roles.MANAGER;
                        case "employee" -> Roles.EMPLOYEE;
                        default -> throw new IllegalStateException("Unexpected value: " + role);
                    };
                    users.add(new User(id,name,phone,email,roleenum));
                }
            } catch (SQLException e) {
                System.out.println("Error while fetching users: " + e.getMessage());
            }
        }
        return users;
    }

    @Override
    public User getUserById(int id) {
        Optional<Connection> opConnection = getConnection();
        if (opConnection.isPresent()) {
            try (Connection con = opConnection.get();
                 PreparedStatement pst = con.prepareStatement("SELECT * FROM users WHERE userId=?")) {
                pst.setInt(1, id);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        String name = rs.getString("name");
                        String role = rs.getString("role");
                        String phone = rs.getString("phone");
                        String email = rs.getString("email");
                        Roles roleenum=switch (role){
                            case "admin" -> Roles.ADMIN;
                            case "manager" -> Roles.MANAGER;
                            case "employee" -> Roles.EMPLOYEE;
                            default -> throw new IllegalStateException("Unexpected value: " + role);
                        };
                        return new User(id,name,phone,email,roleenum);
                    }
                }
            } catch (ResourceNotFoundException | SQLException e) {
                throw new RuntimeException("Error fetching user by ID: " + id, e);
            }
        }
        return null;
    }


    @Override
    public User addUser(User user) {
        Optional<Connection> opConnection = getConnection();
        if (opConnection.isPresent()) {
            try (Connection con = opConnection.get();
                 PreparedStatement pst = con.prepareStatement("INSERT INTO users(userId, name, role, email, phone) VALUES(?,?,?,?,?)")) {
                pst.setInt(1, user.getEmpid());
                pst.setString(2, user.getEmpname());
                pst.setString(3, user.getRole().getRole());
                pst.setString(4, user.getEmail());
                pst.setString(5, user.getPhonenumber());
                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("User added successfully.");
                    return user;
                } else {
                    System.out.println("No rows affected.");
                    return null;
                }

            } catch (ResourceAlreadyExistsException | SQLException e) {
                throw new RuntimeException("Error adding user: " + user.getEmpid(), e);
            }
        } else {
            System.out.println("Connection not available.");
            return null;
        }
    }

    @Override
    public User updateUser(User user) {
        Optional<Connection> opConnection = getConnection();
        if (opConnection.isPresent()) {
            try (Connection con = opConnection.get()) {
                User existingUser = getUserById(user.getEmpid());
                if (existingUser == null) {
                    throw new RuntimeException("User with ID " + user.getEmpid() + " not found.");
                }
                try (PreparedStatement pst = con.prepareStatement("UPDATE users SET name=?, role=?, phone=?, email=? WHERE userId=?")) {
                    pst.setString(1, user.getEmpname());
                    pst.setString(2, user.getRole().getRole());
                    pst.setString(3, user.getPhonenumber());
                    pst.setString(4, user.getEmail());
                    pst.setInt(5, user.getEmpid());
                    int affectedRows = pst.executeUpdate();
                    if (affectedRows > 0) {
                        return user;
                    } else {
                        return null;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error updating user: " + user.getEmpid(), e);
            }
        }
        return null;
    }


    @Override
    public User deleteUserById(int id) {
        Optional<Connection> opConnection = getConnection();
        if (opConnection.isPresent()) {
            try (Connection con = opConnection.get()) {
                User existingUser = getUserById(id);
                if (existingUser == null) {
                    throw new RuntimeException(new ResourceNotFoundException("User with ID " + id + " not found."));
                }

                try (PreparedStatement pst = con.prepareStatement("DELETE FROM users WHERE userId=?")) {
                    pst.setInt(1, id);
                    int affectedRows = pst.executeUpdate();
                    if (affectedRows > 0) {
                        return existingUser;
                    } else {
                        throw new RuntimeException("Failed to delete user with ID " + id);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException("Error executing delete operation for user ID " + id, e);
                }

            } catch (ResourceNotFoundException e) {
                throw new RuntimeException("User not found: " + e.getMessage(), e);
            } catch (SQLException e) {
                throw new RuntimeException("Database error occurred while deleting user with ID " + id, e);
            }
        }
        return null;
    }


    @Override
    public List<MeetingRoom> getMeetingRooms() {
        List<MeetingRoom> rooms = new ArrayList<>();
        Optional<Connection> opConnection = getConnection();
        if (opConnection.isPresent()) {
            try (Connection con = opConnection.get();
                 Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM roomd")) {
                while (rs.next()) {
                    int id = rs.getInt("roomId");
                    String name = rs.getString("roomName");
                    int role = rs.getInt("basicCost");
                    int capacity = rs.getInt("seatingCapacity");
                }
            } catch (SQLException e) {
                System.out.println("Error while fetching users: " + e.getMessage());
            }
        }
        return rooms;
    }

    @Override
    public MeetingRoom getMeetingRoomById(int id) {
        Optional<Connection> opConnection = getConnection();
        if (opConnection.isPresent()) {
            try (Connection con = opConnection.get()) {
                String roomQuery = "SELECT * FROM rooms WHERE roomId=?";
                try (PreparedStatement pst = con.prepareStatement(roomQuery)) {
                    pst.setInt(1, id);
                    try (ResultSet rs = pst.executeQuery()) {
                        if (rs.next()) {
                            int roomId = rs.getInt("roomId");
                            String roomName = rs.getString("roomName");
                            int basicCost = rs.getInt("basicCost");
                            int seatingCapacity = rs.getInt("seatingCapacity");
                            boolean isAvailable = rs.getBoolean("isAvailable");
                            List<Amenity> amenities = new ArrayList<Amenity>();
                            String amenityQuery = "SELECT amenityId FROM roomAmenitiesRef WHERE roomId=?";
                            try (PreparedStatement amenityStmt = con.prepareStatement(amenityQuery)) {
                                amenityStmt.setInt(1, roomId);
                                try (ResultSet amenityRs = amenityStmt.executeQuery()) {
                                    while (amenityRs.next()) {
                                        int amenityId = amenityRs.getInt("amenityId");
                                        Amenity amenity = convertIdToAmenity(amenityId);
                                        if (amenity != null) {
                                            amenities.add(amenity);
                                        }
                                    }
                                }
                            }
                            return new MeetingRoom(roomId, roomName, basicCost, seatingCapacity, amenities, isAvailable);
                        } else {
                            throw new RuntimeException(new ResourceNotFoundException("Meeting room with ID " + id + " not found."));
                        }
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error fetching meeting room by ID: " + id, e);
            }
        }
        return null;
    }

    private Amenity convertIdToAmenity(int amenityId) {
        switch (amenityId) {
            case 1:
                return Amenity.PROJECTOR;
            case 2:
                return Amenity.WIFI;
            case 3:
                return Amenity.CONFERENCE_CALL;
            case 4:
                return Amenity.WHITEBOARD;
            case 5:
                return Amenity.WATER_DISPENSER;
            case 6:
                return Amenity.TV;
            case 7:
                return Amenity.COFFEE_MACHINE;
            default:
                return null;
        }
    }

    @Override
    public MeetingRoom addMeetingRoom(MeetingRoom meetingRoom) {
        Optional<Connection> opConnection = getConnection();
        if (opConnection.isPresent()) {
            try (Connection con = opConnection.get()) {
                con.setAutoCommit(false);
                try {
                    try (PreparedStatement roomPst = con.prepareStatement
                            ("INSERT INTO rooms (roomName, basicCost, seatingCapacity, isAvailable) VALUES (?, ?, ?, ?)",
                                    Statement.RETURN_GENERATED_KEYS)) {
                        roomPst.setString(1, meetingRoom.getName());
                        roomPst.setDouble(2, meetingRoom.getBasicCost());
                        roomPst.setInt(3, meetingRoom.getSeatingCapacity());
                        roomPst.setBoolean(4, meetingRoom.isAvailable());

                        roomPst.executeUpdate();
                        try (ResultSet generatedKeys = roomPst.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                int roomId = generatedKeys.getInt(1);
                                String insertAmenityQuery = "INSERT INTO roomAmenitiesRef (roomId, amenityId, count) VALUES (?, ?, ?)";
                                try (PreparedStatement pst = con.prepareStatement(insertAmenityQuery)) {
                                    for (Amenity amenity : meetingRoom.getAmenities()) {
                                        pst.setInt(1, roomId);
                                        pst.setInt(2, getAmenityId(amenity));
                                        pst.setInt(3, 1);
                                        pst.addBatch();
                                    }
                                    pst.executeBatch();
                                }
                                con.commit();
                                return new MeetingRoom(roomId, meetingRoom.getName(), meetingRoom.getBasicCost(), meetingRoom.getSeatingCapacity(), meetingRoom.getAmenities(), meetingRoom.isAvailable());
                            } else {
                                con.rollback();
                                throw new SQLException("Creating room failed, no ID obtained.");
                            }
                        }
                    } catch (SQLException e) {
                        con.rollback();
                        throw e;
                    }
                } catch (SQLException e) {
                    con.rollback();
                    throw new RuntimeException("Error adding meeting room", e);
                }
            } catch (SQLException e) {
                throw new RuntimeException("Database connection error", e);
            }
        }
        throw new RuntimeException("No database connection available");
    }

    private int getAmenityId(Amenity amenity) {
        switch (amenity) {
            case PROJECTOR:
                return 1;
            case WIFI:
                return 2;
            case CONFERENCE_CALL:
                return 3;
            case WHITEBOARD:
                return 4;
            case WATER_DISPENSER:
                return 5;
            case TV:
                return 6;
            case COFFEE_MACHINE:
                return 7;
            default:
                throw new IllegalArgumentException("Unknown amenity: " + amenity);
        }
    }

    @Override
    public MeetingRoom updateMeetingRoom(MeetingRoom meetingRoom) {
        Optional<Connection> opConnection = getConnection();
        if (opConnection.isPresent()) {
            try (Connection con = opConnection.get()) {
                MeetingRoom existingRoom = getMeetingRoomById(meetingRoom.getRoomId());
                if (existingRoom == null) {
                    throw new RuntimeException(new ResourceNotFoundException("Meeting room with ID " + meetingRoom.getRoomId() + " not found."));
                }
                con.setAutoCommit(false);
                try {
                    String updateRoomQuery = "UPDATE rooms SET roomName=?, basicCost=?, seatingCapacity=?, isAvailable=? WHERE roomId=?";
                    try (PreparedStatement roomStmt = con.prepareStatement(updateRoomQuery)) {
                        roomStmt.setString(1, meetingRoom.getName());
                        roomStmt.setDouble(2, meetingRoom.getBasicCost());
                        roomStmt.setInt(3, meetingRoom.getSeatingCapacity());
                        roomStmt.setBoolean(4, meetingRoom.isAvailable());
                        roomStmt.setInt(5, meetingRoom.getRoomId());

                        int affectedRows = roomStmt.executeUpdate();
                        if (affectedRows == 0) {
                            con.rollback();
                            throw new RuntimeException("Updating room failed, no rows affected.");
                        }
                    }
                    String deleteAmenitiesQuery = "DELETE FROM roomAmenitiesRef WHERE roomId=?";
                    try (PreparedStatement deleteStmt = con.prepareStatement(deleteAmenitiesQuery)) {
                        deleteStmt.setInt(1, meetingRoom.getRoomId());
                        deleteStmt.executeUpdate();
                    }

                    String insertAmenityQuery = "INSERT INTO roomAmenitiesRef (roomId, amenityId, count) VALUES (?, ?, ?)";
                    try (PreparedStatement amenityStmt = con.prepareStatement(insertAmenityQuery)) {
                        for (Amenity amenity : meetingRoom.getAmenities()) {
                            amenityStmt.setInt(1, meetingRoom.getRoomId());
                            amenityStmt.setInt(2, getAmenityId(amenity));
                            amenityStmt.setInt(3, 1);
                            amenityStmt.addBatch();
                        }
                        amenityStmt.executeBatch();
                    }

                    con.commit();
                    return new MeetingRoom(meetingRoom.getRoomId(), meetingRoom.getName(), meetingRoom.getBasicCost(),
                            meetingRoom.getSeatingCapacity(), meetingRoom.getAmenities(), meetingRoom.isAvailable());
                } catch (SQLException e) {
                    con.rollback();
                    throw new RuntimeException("Error updating meeting room", e);
                }
            } catch (SQLException e) {
            }
        }
        throw new RuntimeException("No database connection available");
    }


    @Override
    public MeetingRoom deleteMeetingRoomById(int id) {
        Optional<Connection> opConnection = getConnection();
        if (opConnection.isPresent()) {
            try (Connection con = opConnection.get()) {
                MeetingRoom existingRoom = getMeetingRoomById(id);
                if (existingRoom == null) {
                    throw new RuntimeException(new ResourceNotFoundException("Meeting room with ID " + id + " not found."));
                } else {
                    try (PreparedStatement pst = con.prepareStatement("DELETE FROM roomAmenitiesRef WHERE roomId=?")) {
                        pst.setInt(1, id);
                        int affectedRows = pst.executeUpdate();
                        if (affectedRows > 0) {
                            try (PreparedStatement roomPst = con.prepareStatement("DELETE FROM rooms WHERE roomId=?")) {
                                pst.setInt(1, id);
                                int rows = pst.executeUpdate();
                                if (rows == 0) {
                                    throw new RuntimeException("Error in deleting room");
                                } else {
                                    return new MeetingRoom(existingRoom.getRoomId(), existingRoom.getName(), existingRoom.getBasicCost(),
                                            existingRoom.getSeatingCapacity(), existingRoom.getAmenities(), existingRoom.isAvailable());
                                }
                            } catch (SQLException e) {
                                throw new RuntimeException("Error deleting meeting room", e);
                            }
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException("Error deleting meeting room amenities", e);
                    }
                }
            } catch (SQLException e) {

            }
        }
        throw new RuntimeException("No database connection available");
    }


    @Override
    public List<MeetingRoomRequest> getMeetingRoomRequests() {
        List<MeetingRoomRequest> meetingRoomRequests = new ArrayList<>();
        Optional<Connection> opConnection = getConnection();
        if (opConnection.isPresent()) {
            try (Connection con = opConnection.get();
                 Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM roomRequests")) {
                while (rs.next()) {
                    int id = rs.getInt("bookingId");
                    int roomId = rs.getInt("roomId");
                    int managerId = rs.getInt("managerId");
                    String meetingTitle = rs.getString("meetingTitle");
                    Date meetingDate = rs.getDate("meetingDate");
                    Time startTime = rs.getTime("startTime");
                    Time endTime = rs.getTime("endTime");
                    meetingRoomRequests.add(new MeetingRoomRequest(roomId, managerId, meetingTitle, meetingDate, startTime, endTime));
                }
            } catch (SQLException e) {
                System.out.println("Error while fetching users: " + e.getMessage());
            }
        }
        return meetingRoomRequests;
    }

    @Override
    public MeetingRoomRequest getMeetingRoomRequestById(int id) {
        Optional<Connection> opConnection = getConnection();
        if (opConnection.isPresent()) {
            try (Connection con = opConnection.get();
                 PreparedStatement pst = con.prepareStatement("SELECT * FROM roomRequests WHERE bookingID=?")) {
                pst.setInt(1, id);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    int roomId = rs.getInt("roomId");
                    int managerId = rs.getInt("managerId");
                    String meetingTitle = rs.getString("meetingTitle");
                    Date meetingDate = rs.getDate("meetingDate");
                    Time startTime = rs.getTime("startTime");
                    Time endTime = rs.getTime("endTime");
                    return new MeetingRoomRequest(roomId, managerId, meetingTitle, meetingDate, startTime, endTime);
                } else {
                    throw new RuntimeException(new ResourceNotFoundException("Meeting Room Request with Booking ID:" + id + " is not found"));
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("No database connection available");
    }

    @Override
    public MeetingRoomRequest addMeetingRoomRequest(MeetingRoomRequest meetingRoomRequest) {
        Optional<Connection> opConnection = getConnection();
        if (opConnection.isPresent()) {
            try (Connection con = opConnection.get();
                 PreparedStatement pst = con.prepareStatement("SELECT * FROM roomRequests WHERE bookingID=?")) {
                pst.setInt(1, meetingRoomRequest.getMeetingReqId());
                boolean existingMeetingRoomRequest = pst.execute();
                if (existingMeetingRoomRequest) {
                    throw new RuntimeException(new ResourceAlreadyExistsException("Meeting Room Request with ID:" + meetingRoomRequest.getMeetingReqId() + " already exists"));
                } else {
                    PreparedStatement meetingReqPst = con.prepareStatement("INSERT INTO roomRequests values(?,?,?,?,?,?,?,?)");
                    meetingReqPst.setInt(1, meetingRoomRequest.getMeetingReqId());
                    meetingReqPst.setInt(2, meetingRoomRequest.getManagerId());
                    meetingReqPst.setInt(3, meetingRoomRequest.getRoomId());
                    meetingReqPst.setString(4, meetingRoomRequest.getMeetingTitle());
                    meetingReqPst.setDate(5, meetingRoomRequest.getDate());
                    meetingReqPst.setTime(6, meetingRoomRequest.getStartTime());
                    meetingReqPst.setTime(7, meetingRoomRequest.getEndTime());
                    meetingReqPst.setString(8, "Pending");
                    System.out.println("Successfully Submitted Meeting Room Request");
                    return meetingRoomRequest;

                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException();
    }

    @Override
    public MeetingRoomRequest updateMeetingRoomRequest(MeetingRoomRequest meetingRoomRequest) {
        Optional<Connection> opConnection = getConnection();
        if (opConnection.isPresent()) {
            try (Connection con = opConnection.get();
                 PreparedStatement pst = con.prepareStatement("SELECT * FROM roomRequests WHERE bookingID=?")) {
                pst.setInt(1, meetingRoomRequest.getMeetingReqId());
                boolean existingMeetingRoomRequest = pst.execute();
                if (!existingMeetingRoomRequest) {
                    throw new RuntimeException(new ResourceNotFoundException("Meeting Room Request with ID:" + meetingRoomRequest.getMeetingReqId() + " not found"));
                } else {
                    PreparedStatement meetingReqPst = con.prepareStatement("UPDATE roomRequests set roomId=?, meetingTitle=?, meetingDate=?, startTime=?, endTime=?, status=?");
                    meetingReqPst.setInt(1, meetingRoomRequest.getRoomId());
                    meetingReqPst.setString(2, meetingRoomRequest.getMeetingTitle());
                    meetingReqPst.setDate(3, meetingRoomRequest.getDate());
                    meetingReqPst.setTime(4, meetingRoomRequest.getStartTime());
                    meetingReqPst.setTime(5, meetingRoomRequest.getEndTime());
                    meetingReqPst.setString(6, meetingRoomRequest.getStatus().toString());
                    meetingReqPst.executeUpdate();
                    System.out.println("Meeting Room Request Updated");
                    return meetingRoomRequest;
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException();
    }

    @Override
    public MeetingRoomRequest deleteMeetingRoomRequestById(int id) {
        Optional<Connection> opConnection = getConnection();
        if (opConnection.isPresent()) {
            try (Connection con = opConnection.get();
                 PreparedStatement pst = con.prepareStatement("SELECT * FROM roomRequests WHERE bookingID=?")) {
                pst.setInt(1, id);
                ResultSet rs = pst.executeQuery();
                if (!rs.next()) {
                    throw new RuntimeException(new ResourceNotFoundException("Meeting Room Request with ID: " + id + " not found"));
                } else {
                    try (PreparedStatement deletePst = con.prepareStatement("DELETE FROM roomRequests WHERE bookingID=?")) {
                        deletePst.setInt(1, id);
                        deletePst.executeUpdate();
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("No database connection available");
        }
        throw new RuntimeException();
    }

    @Override
    public List<MeetingRoomRequest> getMeetingRoomRequestsByManagerId(int managerId) {
        return null;
    }

    @Override
    public String getPassword(int empId) {
        Optional<Connection> opConnection = getConnection();
        if (opConnection.isPresent()) {
            try (Connection con = opConnection.get()) {

                try (PreparedStatement pst = con.prepareStatement("SELECT password FROM credentials WHERE userId=?")) {
                    pst.setInt(1, empId);
                    ResultSet rs = pst.executeQuery();
                    if (rs.next()) {
                        return rs.getString("password");
                    } else {
                        throw new RuntimeException(new ResourceNotFoundException("EmpId: " + empId + " is not found or Incorrect"));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("No database connection available");
    }

    @Override
    public List<MeetingRoomTypeRequired> getMeetingRoomTypesWithRequiredAmenities() {
        List<MeetingRoomTypeRequired> meetingRoomAmenities = new ArrayList<>();
        Optional<Connection> opConnection = getConnection();
        if (opConnection.isPresent()) {
            try (Connection con = opConnection.get()) {
                Map<Integer, MeetingRoomType> meetingRoomTypeMap = new HashMap<>();
                String roomTypeQuery = "SELECT id, type FROM meetingRoomTypes";
                try (PreparedStatement pst = con.prepareStatement(roomTypeQuery);
                     ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String type = rs.getString("type");
                        MeetingRoomType meetingRoomType = MeetingRoomType.valueOf(type.toUpperCase().replace(" ", "_"));
                        meetingRoomTypeMap.put(id, meetingRoomType);
                    }
                }
                String roomAmenityQuery = "SELECT meetingTypeID, AmenityID, count FROM meetingRoomTypesAmenities";
                try (PreparedStatement pst = con.prepareStatement(roomAmenityQuery);
                     ResultSet rs = pst.executeQuery()) {
                    Map<Integer, List<Amenity>> roomTypeToAmenitiesMap = new HashMap<>();
                    while (rs.next()) {
                        int meetingTypeID = rs.getInt("meetingTypeID");
                        int amenityID = rs.getInt("AmenityID");
                        int count = rs.getInt("count");
                        Amenity amenity = convertIdToAmenity(amenityID);
                        if (amenity != null) {
                            amenity.setCount(count);
                            roomTypeToAmenitiesMap.computeIfAbsent(meetingTypeID, k -> new ArrayList<>()).add(amenity);
                        }
                    }
                    for (Map.Entry<Integer, MeetingRoomType> entry : meetingRoomTypeMap.entrySet()) {
                        Integer meetingTypeID = entry.getKey();
                        MeetingRoomType meetingRoomType = entry.getValue();

                        List<Amenity> amenities = roomTypeToAmenitiesMap.getOrDefault(meetingTypeID, new ArrayList<>());

                        MeetingRoomTypeRequired meetingRoomTypeRequired = new MeetingRoomTypeRequired(meetingRoomType, amenities);
                        meetingRoomAmenities.add(meetingRoomTypeRequired);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error while retrieving meeting room types and amenities", e);
            }
        } else {
            throw new RuntimeException("No database connection available");
        }
        return meetingRoomAmenities;
    }

    @Override
    public MeetingRoomTypeRequired getMeetingRoomTypeByType(MeetingRoomType type) {
        List<Amenity> amenityList=new ArrayList<>();
        Optional<Connection> opConnection = getConnection();
        if (opConnection.isPresent()) {
            try (Connection con = opConnection.get()) {
                int meetingTypeId=getMeetingTypeId(type);
                pst=con.prepareStatement("SELECT amenityId FROM meetingRoomAmenities WHERE meetingTypeID=?");
                pst.setInt(1,meetingTypeId);
                try (ResultSet rs = pst.executeQuery()) {

                    while (rs.next()) {
                        int amenityId = rs.getInt("amenityId");
                        amenityList.add(convertIdToAmenity(amenityId));
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error fetching amenity IDs for meeting type", e);
            }
        }
        return new MeetingRoomTypeRequired(type,amenityList);
    }

    private int getMeetingTypeId(MeetingRoomType type) {
        switch (type) {
            case ClASSROOM_TRAINING:
                return 1;
            case ONLINE_TRAINING:
                return 2;
            case CONFERENCE_CALL:
                return 3;
            case BUSINESS:
                return 4;
            default:
                return 0;

        }
    }
}