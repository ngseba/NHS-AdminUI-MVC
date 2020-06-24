package ro.iteahome.nhs.adminui.exception.business;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException() {
        super("ROLE NOT FOUND.");
    }
}
