import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import interfaces.ServiceLayer;
import layers.serviceLayer.ServiceFactory;
import layers.serviceLayer.ServiceLayerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import models.MeetingRoom;
import models.MeetingRoomRequest;
import models.User;
import utils.*;
import exceptions.NotAuthorizedException;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.io.File;

class ServiceLayerProxyImplTest {
    private ServiceLayer serviceLayerProxy;
    private ServiceLayer serviceLayer;
    private User manager;
    private User employee;

    @BeforeEach
    void setUp() {
        serviceLayer = mock(ServiceLayerImpl.class);
        manager = new User(1, "Manager", "1234567890", "manager@example.com", Roles.MANAGER);
        employee = new User(2, "Employee", "9876543210", "employee@example.com", Roles.EMPLOYEE);
    }

    // Tests for Employee accessing Admin functions
    @Test
    void testGetAllMeetingRoomsUnauthorizedForEmployee() {
        serviceLayerProxy = ServiceFactory.getService(employee);
        assertThrows(NotAuthorizedException.class, serviceLayerProxy::getAllMeetingRooms);
    }

    @Test
    void testAddMeetingRoomUnauthorizedForEmployee() {
        serviceLayerProxy = ServiceFactory.getService(employee);
        assertThrows(NotAuthorizedException.class, () -> serviceLayerProxy.addMeetingRoom(new MeetingRoom(1, "Room A", 10, 10, null, false)));
    }

    @Test
    void testDeleteMeetingRoomUnauthorizedForEmployee() {
        serviceLayerProxy = ServiceFactory.getService(employee);
        assertThrows(NotAuthorizedException.class, () -> serviceLayerProxy.deleteMeetingRoom(1));
    }

    @Test
    void testUpdateMeetingRoomRequestStatusUnauthorizedForEmployee() {
        serviceLayerProxy = ServiceFactory.getService(employee);
        assertThrows(NotAuthorizedException.class, () -> serviceLayerProxy.updateMeetingRoomRequest(1, MeetingRoomRequestStatus.APPROVED));
    }

    @Test
    void testImportUsersUnauthorizedForEmployee() {
        serviceLayerProxy = ServiceFactory.getService(employee);
        assertThrows(NotAuthorizedException.class, () -> serviceLayerProxy.importUsers(new File("path/to/file")));
    }

    // Tests for Manager accessing Admin functions
    @Test
    void testAddMeetingRoomUnauthorizedForManager() {
        serviceLayerProxy = ServiceFactory.getService(manager);
        assertThrows(NotAuthorizedException.class, () -> serviceLayerProxy.addMeetingRoom(new MeetingRoom(1, "Room B", 20, 15, null, false)));
    }

    @Test
    void testDeleteMeetingRoomUnauthorizedForManager() {
        serviceLayerProxy = ServiceFactory.getService(manager);
        assertThrows(NotAuthorizedException.class, () -> serviceLayerProxy.deleteMeetingRoom(2));
    }

    @Test
    void testImportUsersUnauthorizedForManager() {
        serviceLayerProxy = ServiceFactory.getService(manager);
        assertThrows(NotAuthorizedException.class, () -> serviceLayerProxy.importUsers(new File("path/to/anotherfile")));
    }

    // Tests for Employee accessing Manager functions
    @Test
    void testGetAvailableMeetingRoomsUnauthorizedForEmployee() {
        serviceLayerProxy = ServiceFactory.getService(employee);
        assertThrows(NotAuthorizedException.class, serviceLayerProxy::getAvailableMeetingRooms);
    }

    @Test
    void testRequestForMeetingRoomUnauthorizedForEmployee() {
        serviceLayerProxy = ServiceFactory.getService(employee);
        MeetingRoomRequest request = new MeetingRoomRequest(1, 1, "Meeting", Date.valueOf("2024-08-25"), Time.valueOf("10:00:00"), Time.valueOf("11:00:00"));
        assertThrows(NotAuthorizedException.class, () -> serviceLayerProxy.requestForMeetingRoom(request));
    }

    @Test
    void testGetMeetingRoomsWithTypeUnauthorizedForEmployee() {
        serviceLayerProxy = ServiceFactory.getService(employee);
        assertThrows(NotAuthorizedException.class, () -> serviceLayerProxy.getMeetingRoomsWithType(MeetingRoomType.ClASSROOM_TRAINING));
    }

    @Test
    void testAddUsersToScheduledMeetingRoomUnauthorizedForEmployee() {
        serviceLayerProxy = ServiceFactory.getService(employee);
        assertThrows(NotAuthorizedException.class, () -> serviceLayerProxy.addUsersToScheduledMeetingRoom(1, 1, List.of(new User(1, "User A", "1234567890", "usera@example.com", Roles.EMPLOYEE))));
    }

    @Test
    void testRemoveUsersFromScheduledMeetingRoomUnauthorizedForEmployee() {
        serviceLayerProxy = ServiceFactory.getService(employee);
        assertThrows(NotAuthorizedException.class, () -> serviceLayerProxy.removeUsersFromScheduledMeetingRoom(1, 1, List.of(new User(1, "User B", "1234567890", "userb@example.com", Roles.EMPLOYEE))));
    }


}
