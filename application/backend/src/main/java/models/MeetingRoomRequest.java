package models;

import utils.MeetingRoomRequestStatus;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class MeetingRoomRequest {
    private int meetingReqId;
    private int roomId;
    private int managerid;
    private  String meetingTitle;
    private Date date;
    private Time startTime;
    private Time endTime;
    private final double totalDurationinHours;
    private MeetingRoomRequestStatus status;
    private List<User> userList;

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public MeetingRoomRequest(int roomId, int managerid, String meetingTitle, Date date, Time startTime, Time endTime) {
        this.roomId = roomId;
        this.managerid = managerid;
        this.meetingTitle = meetingTitle;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalDurationinHours=getHours(this.startTime,this.endTime);
        this.userList=new ArrayList<User>();
    }

    public String getMeetingTitle() {
        return meetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMeetingReqId() {
        return meetingReqId;
    }

    public void setMeetingReqId(int meetingReqId) {
        this.meetingReqId = meetingReqId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getManagerId() {
        return managerid;
    }

    public void setManagerId(int managerid) {
        this.managerid = managerid;
    }

    public MeetingRoomRequestStatus getStatus() {
        return status;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public double getTotalDurationinHours() {
        return totalDurationinHours;
    }

    public void setStatus(MeetingRoomRequestStatus status) {
        this.status = status;
    }
    private int getHours(Time t1,Time t2){
        int milliseconds= (int) (t2.getTime()-t1.getTime());
        return (((milliseconds/1000)/60))/60;

    }
}
