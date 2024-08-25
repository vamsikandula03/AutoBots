package utils;

public enum Roles {
    ADMIN("Admin"),
    MANAGER("Manager"),
    EMPLOYEE("Employee");
final String role;
    Roles(String role) {
        this.role=role;
    }
    public String getRole() {
        return role;
    }
}
