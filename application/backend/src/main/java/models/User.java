package models;


import utils.Roles;

public class User {
    private int empid;
    private String empname;
    private String phonenumber;
    private String email;
    private Roles role;
    private int credits=0;

    public User(int empid, String empname, String phonenumber, String email, Roles role) {
        this.empid = empid;
        this.empname = empname;
        this.phonenumber = phonenumber;
        this.email = email;
        this.role = role;
    }

    public int getEmpid() {
        return empid;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public Roles getRole() {
        return role;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setRole(Roles role) {
        this.role = role;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}
