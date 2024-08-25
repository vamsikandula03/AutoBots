import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import models.MeetingRoom;
import models.MeetingRoomRequest;
import models.MeetingRoomTypeRequired;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import exceptions.CredentialsException;
import models.User;
import layers.serviceLayer.ServiceLayerImpl;
import interfaces.DAOLayer;
import utils.Amenity;
import layers.DAOLayer.DAOFactory;
import utils.MeetingRoomRequestStatus;
import utils.MeetingRoomType;
import utils.Roles;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

class ServiceLayerImplTest {
    private ServiceLayerImpl serviceLayer;
    private DAOLayer dao;

    @BeforeEach
    void setUp() {
        dao = mock(DAOLayer.class);
        serviceLayer = new ServiceLayerImpl();
        serviceLayer.setDao(dao); // Adjust if necessary
    }

    @Test
    void testLoginSuccess() throws CredentialsException {
        int userId = 1;
        String password = "password";
        User user = new User(userId, "John Doe", "1234567890", "john@example.com", Roles.EMPLOYEE);

        when(dao.getPassword(userId)).thenReturn(password);
        when(dao.getUserById(userId)).thenReturn(user);

        User result = serviceLayer.Login(userId, password);
        assertEquals(user, result);
    }

    @Test
    void testLoginFailure() {
        int userId = 1;
        String password = "wrongpassword";

        when(dao.getPassword(userId)).thenReturn("correctpassword");

        assertThrows(CredentialsException.class, () -> {
            serviceLayer.Login(userId, password);
        });
    }
    @Test
    void testGetAvailableMeetingRooms() {
        MeetingRoom room1 = new MeetingRoom(1, "Room1", 100, 10, List.of(), true);
        MeetingRoom room2 = new MeetingRoom(2, "Room2", 200, 20, List.of(), false);

        when(dao.getMeetingRooms()).thenReturn(List.of(room1, room2));

        List<MeetingRoom> availableRooms = serviceLayer.getAvailableMeetingRooms();
        assertEquals(List.of(room1), availableRooms);
    }
    @Test
    void testRequestForMeetingRoomSuccess() {
        MeetingRoomRequest request = new MeetingRoomRequest(1, 1, "Meeting", Date.valueOf("2024-08-25"), Time.valueOf("10:00:00"), Time.valueOf("11:00:00"));
        MeetingRoom room = new MeetingRoom(1, "Room1", 100, 10, List.of(), true);
        User manager = new User(1, "Manager", "1234567890", "manager@example.com", Roles.MANAGER);

        when(dao.getMeetingRoomById(request.getRoomId())).thenReturn(room);
        when(dao.getUserById(request.getManagerId())).thenReturn(manager);
        when(dao.getMeetingRoomRequests()).thenReturn(List.of());

        String result = serviceLayer.requestForMeetingRoom(request);
        assertEquals("Meeting room request submitted successfully", result);
    }

    @Test
    void testRequestForMeetingRoomFailureDueToAvailability() {
        MeetingRoomRequest request = new MeetingRoomRequest(1, 1, "Meeting", Date.valueOf("2024-08-25"), Time.valueOf("10:00:00"), Time.valueOf("11:00:00"));
        MeetingRoom room = new MeetingRoom(1, "Room1", 100, 10, List.of(), false);
        User manager = new User(1, "Manager", "1234567890", "manager@example.com", Roles.MANAGER);

        when(dao.getMeetingRoomById(request.getRoomId())).thenReturn(room);
        when(dao.getUserById(request.getManagerId())).thenReturn(manager);

        String result = serviceLayer.requestForMeetingRoom(request);
        assertEquals("Meeting room not available", result);
    }
    @Test
    void testGetScheduledMeetingRooms() {
        MeetingRoomRequest request = new MeetingRoomRequest(1, 1, "Meeting", Date.valueOf("2024-08-25"), Time.valueOf("10:00:00"), Time.valueOf("11:00:00"));
        MeetingRoom room = new MeetingRoom(1, "Room1", 100, 10, List.of(), true);

        when(dao.getMeetingRoomRequests()).thenReturn(List.of(request));
        when(dao.getMeetingRoomById(request.getRoomId())).thenReturn(room);

        List<MeetingRoom> scheduledRooms = serviceLayer.getScheduledMeetingRooms(1);
        assertEquals(List.of(room), scheduledRooms);
    }
    @Test
    void testAddUsersToScheduledMeetingRoom() {
        MeetingRoomRequest request = new MeetingRoomRequest(1, 1, "Meeting", Date.valueOf("2024-08-25"), Time.valueOf("10:00:00"), Time.valueOf("11:00:00"));
        User user = new User(2, "User", "1234567890", "user@example.com", Roles.EMPLOYEE);

        when(dao.getMeetingRoomRequestById(1)).thenReturn(request);
        when(dao.updateMeetingRoomRequest(request)).thenReturn(request);

        boolean result = serviceLayer.addUsersToScheduledMeetingRoom(1, 1, List.of(user));
        assertTrue(result);
    }

    @Test
    void testRemoveUsersFromScheduledMeetingRoom() {
        MeetingRoomRequest request = new MeetingRoomRequest(1, 1, "Meeting", Date.valueOf("2024-08-25"), Time.valueOf("10:00:00"), Time.valueOf("11:00:00"));
        User user = new User(2, "User", "1234567890", "user@example.com", Roles.EMPLOYEE);

        request.setUserList(new ArrayList<>(List.of(user)));

        when(dao.getMeetingRoomRequestById(1)).thenReturn(request);
        when(dao.updateMeetingRoomRequest(request)).thenReturn(request);

        boolean result = serviceLayer.removeUsersFromScheduledMeetingRoom(1, 1, List.of(user));
        assertTrue(result);
    }
    @Test
    void testGetMeetingRoomsWithType() {
        MeetingRoom room1 = new MeetingRoom(1, "Room1", 100, 10, List.of(Amenity.PROJECTOR), true);
        MeetingRoom room2 = new MeetingRoom(2, "Room2", 200, 20, List.of(Amenity.WIFI), true);
        MeetingRoomTypeRequired typeRequired = new MeetingRoomTypeRequired(MeetingRoomType.BUSINESS, List.of(Amenity.PROJECTOR));

        when(dao.getMeetingRoomTypeByType(MeetingRoomType.BUSINESS)).thenReturn(typeRequired);
        when(dao.getMeetingRooms()).thenReturn(List.of(room1, room2));

        List<MeetingRoom> rooms = serviceLayer.getMeetingRoomsWithType(MeetingRoomType.BUSINESS);
        assertEquals(List.of(room1), rooms);
    }
    @Test
    void testAddMeetingRoom() {
        MeetingRoom room = new MeetingRoom(1, "Room1", 100, 10, List.of(), true);

        when(dao.addMeetingRoom(room)).thenReturn(room);

        MeetingRoom result = serviceLayer.addMeetingRoom(room);
        assertEquals(room, result);
    }

    @Test
    void testUpdateMeetingRoom() {
        MeetingRoom room = new MeetingRoom(1, "Room1", 100, 10, List.of(), true);

        when(dao.getMeetingRoomById(1)).thenReturn(room);
        when(dao.updateMeetingRoom(room)).thenReturn(room);

        MeetingRoom result = serviceLayer.updateMeetingRoom(1);
        assertEquals(room, result);
    }

    @Test
    void testDeleteMeetingRoom() {
        MeetingRoom room = new MeetingRoom(1, "Room1", 100, 10, List.of(), true);

        when(dao.deleteMeetingRoomById(1)).thenReturn(room);

        MeetingRoom result = serviceLayer.deleteMeetingRoom(1);
        assertEquals(room, result);
    }
    @Test
    void testUpdateMeetingRoomRequest() {
        MeetingRoomRequest request = new MeetingRoomRequest(1, 1, "Meeting", Date.valueOf("2024-08-25"), Time.valueOf("10:00:00"), Time.valueOf("11:00:00"));

        when(dao.getMeetingRoomRequestById(1)).thenReturn(request);
        when(dao.updateMeetingRoomRequest(request)).thenReturn(request);

        String result = serviceLayer.updateMeetingRoomRequest(1, MeetingRoomRequestStatus.APPROVED);
        assertEquals("Meeting room request status updated successfully", result);
    }




}
