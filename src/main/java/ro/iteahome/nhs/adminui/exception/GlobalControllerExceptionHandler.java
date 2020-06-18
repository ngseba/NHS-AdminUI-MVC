package ro.iteahome.nhs.adminui.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ro.iteahome.nhs.adminui.exception.business.AdminAlreadyExistsException;
import ro.iteahome.nhs.adminui.exception.business.AdminNotFoundException;
import ro.iteahome.nhs.adminui.exception.error.UserError;

public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

//  USER EXCEPTIONS: ---------------------------------------------------------------------------------------------------

    @ExceptionHandler(AdminNotFoundException.class)
    public ResponseEntity<UserError> handleAdminNotFoundException(AdminNotFoundException ex) {
        return new ResponseEntity<>(new UserError("USR-01", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AdminAlreadyExistsException.class)
    public ResponseEntity<UserError> handleAdminAlreadyExistsException(AdminAlreadyExistsException ex) {
        return new ResponseEntity<>(new UserError("USR-02", ex.getMessage()), HttpStatus.CONFLICT);
    }
}
