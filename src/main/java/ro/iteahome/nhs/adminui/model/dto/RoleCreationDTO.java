package ro.iteahome.nhs.adminui.model.dto;

import javax.validation.constraints.NotNull;

public class RoleCreationDTO {

// FIELDS: -------------------------------------------------------------------------------------------------------------

    // NO ID.

    @NotNull
    private String name;

// METHODS: ------------------------------------------------------------------------------------------------------------

    public RoleCreationDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
