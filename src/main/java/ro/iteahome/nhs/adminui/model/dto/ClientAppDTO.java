package ro.iteahome.nhs.adminui.model.dto;

import ro.iteahome.nhs.adminui.model.entity.Role;

import javax.validation.constraints.NotNull;

public class ClientAppDTO {
    private int id;
    @NotNull(message = "NAME CANNOT BE EMPTY.")
    private String name;
    @NotNull (message = "PASSWORD CANNOT BE EMPTY.")
    private String password;
    @NotNull (message = "ROLE NAME CANNOT BE EMPTY.")
    private String roleName;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getRoleName() {
        return roleName;
    }
}
