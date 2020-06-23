package ro.iteahome.nhs.adminui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ro.iteahome.nhs.adminui.model.dto.AdminDTO;
import ro.iteahome.nhs.adminui.model.entity.Admin;
import ro.iteahome.nhs.adminui.model.dto.AdminCredentialsForm;
import ro.iteahome.nhs.adminui.service.AdminService;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admins")
public class AdminController {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private AdminService adminService;

// LINK "GET" REQUESTS: ------------------------------------------------------------------------------------------------

    @GetMapping("/add-form")
    public String showAddForm(Admin admin) {
        return "admin/admin-add-form";
    }

    @GetMapping("/get-form")
    public String showGetForm(AdminDTO adminDTO) {
        return "admin/admin-get-form";
    }

    @GetMapping("/update-verification")
    public String showUpdateVerificationForm(AdminCredentialsForm adminCredentialsForm) {
        return "admin/admin-update-verification";
    }

    @GetMapping("/update-form")
    public ModelAndView showUpdateForm(@Valid AdminCredentialsForm adminCredentialsForm) {
        ModelAndView adminUpdateMV = new ModelAndView("admin/admin-update-form");
        Admin admin = adminService.getByCredentials(adminCredentialsForm.getEmail(), adminCredentialsForm.getPassword());
        adminUpdateMV.addObject(admin);
        return adminUpdateMV;
    }

    @GetMapping("/delete-form")
    public String showDeleteVerificationForm(AdminCredentialsForm adminCredentialsForm) {
        return "admin/admin-delete-form";
    }

// METHODS: ------------------------------------------------------------------------------------------------------------

    // TODO: Incorporate exception handling.

    @PostMapping
    public ModelAndView add(@Valid Admin admin) {
        ModelAndView addAdminMV = new ModelAndView("admin/temp-crud-result");
        AdminDTO adminDTO = adminService.add(admin);
        addAdminMV.addObject(adminDTO);
        return addAdminMV;
    }

    @GetMapping("/by-id")
    public ModelAndView getById(AdminDTO adminDTO) {
        ModelAndView getAdminMV = new ModelAndView("admin/temp-crud-result");
        AdminDTO databaseAdminDTO = adminService.getById(adminDTO.getId());
        getAdminMV.addObject(databaseAdminDTO);
        return getAdminMV;
    }

    @GetMapping("/by-email")
    public ModelAndView getByEmail(AdminDTO adminDTO) {
        ModelAndView getAdminMV = new ModelAndView("admin/temp-crud-result");
        AdminDTO databaseAdminDTO = adminService.getByEmail(adminDTO.getEmail());
        getAdminMV.addObject(databaseAdminDTO);
        return getAdminMV;
    }

    @PostMapping("/updated-admin")
    public ModelAndView update(@Valid Admin admin) {
        ModelAndView adminUpdateResultMV = new ModelAndView("admin/temp-crud-result");
        AdminDTO adminDTO = adminService.update(admin);
        adminUpdateResultMV.addObject(adminDTO);
        return adminUpdateResultMV;
    }

    @PostMapping("/deleted-admin")
    public ModelAndView delete(@Valid AdminCredentialsForm adminCredentialsForm) {
        ModelAndView adminDeleteMV = new ModelAndView("admin/temp-crud-result");
        AdminDTO adminDTO = adminService.deleteByCredentials(adminCredentialsForm.getEmail(), adminCredentialsForm.getPassword());
        adminDeleteMV.addObject(adminDTO);
        return adminDeleteMV;
    }

// OTHER METHODS: ------------------------------------------------------------------------------------------------------

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("errorCode", "ADM-00");
        errors.put("errorMessage", "ADMIN FIELDS HAVE VALIDATION ERRORS.");
        errors.putAll(ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage)));
        return new ResponseEntity<>(
                errors,
                HttpStatus.BAD_REQUEST);
    }
}
