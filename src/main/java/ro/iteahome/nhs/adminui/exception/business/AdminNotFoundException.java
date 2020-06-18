package ro.iteahome.nhs.adminui.exception.business;

public class AdminNotFoundException extends RuntimeException{

    public AdminNotFoundException() {
        super("USER NOT FOUND.");
    }
}
