package ro.iteahome.nhs.adminui.exception.business;

public class RoleAlreadyExistsException extends RuntimeException {

    public RoleAlreadyExistsException() {
        super("ROLE ALREADY EXISTS.");
    }
}
