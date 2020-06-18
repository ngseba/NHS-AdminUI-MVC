package ro.iteahome.nhs.adminui.exception.business;

public class AdminAlreadyExistsException extends RuntimeException{

    public AdminAlreadyExistsException() {
        super("USER ALREADY EXISTS.");
    }
}
