package ro.iteahome.nhs.adminui.controller;

import org.modelmapper.ModelMapper;
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
import ro.iteahome.nhs.adminui.model.dto.AdminCreationForm;
import ro.iteahome.nhs.adminui.model.dto.AdminDTO;
import ro.iteahome.nhs.adminui.model.entity.Admin;
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

    @Autowired
    private ModelMapper modelMapper;

// LINK "GET" REQUESTS: ------------------------------------------------------------------------------------------------

    @GetMapping("/add-form")
    public String showAddForm(Admin admin) {
        return "admin/add-form";
    }

    @GetMapping("/get-form")
    public String showGetForm(AdminDTO adminDTO) {
        return "admin/get-form";
    }

    @GetMapping("/update-search-form")
    public String showUpdateSearchForm(AdminDTO adminDTO) {
        return "admin/update-search-form";
    }

    @GetMapping("/delete-form")
    public String showDeleteForm(AdminDTO adminDTO) {
        return "admin/delete-form";
    }

// METHODS: ------------------------------------------------------------------------------------------------------------

    // TODO: Incorporate exception handling.

    @PostMapping
    public ModelAndView add(@Valid AdminCreationForm admin) {
        ModelAndView addAdminMV = new ModelAndView("admin/temp-crud-result");
        AdminDTO adminDTO = adminService.add(admin);
        addAdminMV.addObject(adminDTO);
        return addAdminMV;
    }

    @GetMapping("/by-id") // TODO: Leaving the field blank throws an error. Fix that.
    public ModelAndView getById(AdminDTO adminDTO) {
        ModelAndView getAdminMV = new ModelAndView("admin/temp-crud-result");
        AdminDTO databaseAdminDTO = adminService.findById(adminDTO.getId());
        getAdminMV.addObject(databaseAdminDTO);
        return getAdminMV;
    }

    @GetMapping("/by-email") // TODO: Leaving the field blank throws an error. Fix that.
    public ModelAndView getByEmail(AdminDTO adminDTO) {
        ModelAndView getAdminMV = new ModelAndView("admin/temp-crud-result");
        AdminDTO databaseAdminDTO = adminService.findByEmail(adminDTO.getEmail());
        getAdminMV.addObject(databaseAdminDTO);
        return getAdminMV;
    }

    @PostMapping("/update-search-term")
    public ModelAndView showPopulatedUpdateForm(AdminDTO adminDTO) {
        ModelAndView adminUpdateMV = new ModelAndView("admin/update-form");
        Admin admin;
        if (adminDTO.getId() != 0) {
            admin = adminService.findSensitiveById(adminDTO.getId());
            adminUpdateMV.addObject(admin);
            return adminUpdateMV;
        } else if (adminDTO.getEmail() != null) {
            admin = adminService.findSensitiveByEmail(adminDTO.getEmail());
            adminUpdateMV.addObject(admin);
            return adminUpdateMV;
        } else {
            adminUpdateMV.setViewName("/update-search-form");
            return adminUpdateMV;
        }
    }

    @PostMapping("/updated-admin")
    public ModelAndView update(@Valid AdminCreationForm admin) {
        ModelAndView adminUpdateResultMV = new ModelAndView("admin/temp-crud-result");
        AdminDTO adminDTO = adminService.update(admin);
        adminUpdateResultMV.addObject(adminDTO);
        return adminUpdateResultMV;
    }

    @PostMapping("/delete-search-term")
    public ModelAndView deleteByIdOrEmail(AdminDTO adminDTO) {
        ModelAndView adminDeleteMV = new ModelAndView("admin/temp-crud-result");
        AdminDTO deletedAdminDTO;
        if (adminDTO.getId() != 0) {
            deletedAdminDTO = adminService.findById(adminDTO.getId());
            adminService.deleteById(adminDTO.getId());
            adminDeleteMV.addObject(deletedAdminDTO);
            return adminDeleteMV;
        } else if (adminDTO.getEmail() != null) {
            deletedAdminDTO = adminService.findByEmail(adminDTO.getEmail());
            adminService.deleteByEmail(adminDTO.getEmail());
            adminDeleteMV.addObject(deletedAdminDTO);
            return adminDeleteMV;
        } else {
            adminDeleteMV.setViewName("/delete-form");
            return adminDeleteMV;
        }
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
