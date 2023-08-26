package cs211.project.models;

public class User {
    private String id;
    private String username;
    private String name;
    private String role;
    private String status;
    private String lastLogin;

    public User(String idCol, String usernameCol, String nameCol, String roleCol, String statusCol, String lastLoginCol) {
        this.id = idCol;
        this.username = usernameCol;
        this.name = nameCol;
        this.role = roleCol;
        this.status = statusCol;
        this.lastLogin = lastLoginCol;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    // todo: password should be hashed (BCrypt)
}
