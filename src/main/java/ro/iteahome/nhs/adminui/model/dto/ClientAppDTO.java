package ro.iteahome.nhs.adminui.model.dto;

import ro.iteahome.nhs.adminui.model.entity.Role;

import javax.validation.constraints.NotNull;

public class ClientAppDTO {

    private int id;

    private String name;

    private String password;

    private String roleName;

// METHODS: ------------------------------------------------------------------------------------------------------------

    public ClientAppDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
