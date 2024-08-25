create database codefury ;
use codefury;
CREATE TABLE users (userId int primary key, name varchar (30), role varchar(10), email varchar(20), phone varchar(10));
Insert into users(userId,name,role,email,phone) values (45393718,"Vamsi Kandula","admin","vamsi@gmail.com","6302710271");

CREATE TABLE credentials (userId int, password varchar(16), FOREIGN KEY (userId) REFERENCES users(userId));

CREATE TABLE meetingType(meetingTypeId int primary key, meetingTypeName varchar(30));
INSERT INTO meetingType (meetingTypeId, meetingTypeName) VALUES (1, 'Classroom Training');
INSERT INTO meetingType (meetingTypeId, meetingTypeName) VALUES (2, 'Online Training');
INSERT INTO meetingType (meetingTypeId, meetingTypeName) VALUES (3, 'Conference Call');
INSERT INTO meetingType (meetingTypeId, meetingTypeName) VALUES (4, 'Business');


CREATE TABLE amenities (amenityId int primary key, amaenityName varchar(20), credits int);

INSERT INTO amenities (amenityId, amaenityName, credits) VALUES (1, 'projector', 5);
INSERT INTO amenities (amenityId, amaenityName, credits) VALUES (2, 'wifi', 10);
INSERT INTO amenities (amenityId, amaenityName, credits) VALUES (3, 'conference', 15);
INSERT INTO amenities (amenityId, amaenityName, credits) VALUES (4, 'whiteboard', 5);
INSERT INTO amenities (amenityId, amaenityName, credits) VALUES (5, 'water', 5);
INSERT INTO amenities (amenityId, amaenityName, credits) VALUES (6, 'tv', 10);
INSERT INTO amenities (amenityId, amaenityName, credits) VALUES (7, 'coffee', 10);

CREATE TABLE meetingRoomAmenities (meetingTypeId int, amenityId int,count int,
FOREIGN KEY (meetingTypeId) REFERENCES meetingType(meetingTypeId),
FOREIGN KEY (amenityId) REFERENCES amenities(amenityId),
primary key(meetingTypeId,amenityId)
);

INSERT INTO meetingRoomAmenities (meetingTypeId, amenityId, count) VALUES (1, 1, 1);
INSERT INTO meetingRoomAmenities (meetingTypeId, amenityId, count) VALUES (1, 4, 1);
INSERT INTO meetingRoomAmenities (meetingTypeId, amenityId, count) VALUES (2, 2, 1);
INSERT INTO meetingRoomAmenities (meetingTypeId, amenityId, count) VALUES (2, 3, 1);
INSERT INTO meetingRoomAmenities (meetingTypeId, amenityId, count) VALUES (3, 3, 1);
INSERT INTO meetingRoomAmenities (meetingTypeId, amenityId, count) VALUES (4, 1, 1);

CREATE TABLE rooms (roomId int primary key, roomName varchar(20), basicCost int, seatingCapacity int);
INSERT INTO rooms (roomId, roomName, basicCost, seatingCapacity) VALUES (1, 'Davinci', 20, 20);
INSERT INTO rooms (roomId, roomName, basicCost, seatingCapacity) VALUES (2, 'Einstein', 10, 10);
INSERT INTO rooms (roomId, roomName, basicCost, seatingCapacity) VALUES (3, 'Galileo', 0, 5);
INSERT INTO rooms (roomId, roomName, basicCost, seatingCapacity) VALUES (4, 'Edison', 20, 30);
INSERT INTO rooms (roomId, roomName, basicCost, seatingCapacity) VALUES (5, 'Tesla', 20, 40);

CREATE TABLE roomAmenitiesRef (roomId int, amenityId int, count int);

INSERT INTO roomAmenitiesRef (roomId, amenityId, count) VALUES (1, 1, 1);
INSERT INTO roomAmenitiesRef (roomId, amenityId, count) VALUES (1, 4, 1);
INSERT INTO roomAmenitiesRef (roomId, amenityId, count) VALUES (1, 7, 3);
INSERT INTO roomAmenitiesRef (roomId, amenityId, count) VALUES (2, 3, 1);
INSERT INTO roomAmenitiesRef (roomId, amenityId, count) VALUES (2, 4, 3);
INSERT INTO roomAmenitiesRef (roomId, amenityId, count) VALUES (2, 5, 1);
INSERT INTO roomAmenitiesRef (roomId, amenityId, count) VALUES (3, 1, 1);
INSERT INTO roomAmenitiesRef (roomId, amenityId, count) VALUES (3, 2, 2);
INSERT INTO roomAmenitiesRef (roomId, amenityId, count) VALUES (4, 3, 1);
INSERT INTO roomAmenitiesRef (roomId, amenityId, count) VALUES (4, 4, 1);
INSERT INTO roomAmenitiesRef (roomId, amenityId, count) VALUES (4, 5, 1);
INSERT INTO roomAmenitiesRef (roomId, amenityId, count) VALUES (4, 6, 1);
INSERT INTO roomAmenitiesRef (roomId, amenityId, count) VALUES (4, 7, 1);


CREATE TABLE roomRequests(bookingId int primary key, managerId int, roomId int, meetingTitle varchar(50), meetingDate date, startTime time, endTime time, status varchar(15));

CREATE TABLE attendees(bookingId int , userId int,
FOREIGN KEY (bookingId) REFERENCES roomRequests(bookingId),
FOREIGN KEY (userId) REFERENCES users(userId));






