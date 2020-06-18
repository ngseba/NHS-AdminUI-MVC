package ro.iteahome.nhs.adminui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ro.iteahome.nhs.adminui.model.form.AdminCredentialsForm;
import ro.iteahome.nhs.adminui.model.dto.AdminDTO;
import ro.iteahome.nhs.adminui.model.entity.Admin;
import ro.iteahome.nhs.adminui.service.AdminService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admins")
public class AdminController {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private AdminService adminService;

// LINK "GET" REQUESTS: --------------------------------------------------------------------------------------------

    @GetMapping("/add-form")
    public String showAddForm(Admin admin) {
        return "admin/add-form";
    }

    @GetMapping("/get-form")
    public String showGetForm(AdminDTO adminDTO) {
        return "admin/get-form";
    }

    @GetMapping("/update-verification-form")
    public String showUpdateVerificationForm(AdminCredentialsForm adminCredentialsForm) {
        return "admin/update-verification-form";
    }

    @GetMapping("/delete-form")
    public String showDeleteVerificationForm(AdminCredentialsForm adminCredentialsForm) {
        return "admin/delete-form";
    }

// METHODS: ------------------------------------------------------------------------------------------------------------

    @PostMapping
    public ModelAndView add(@Valid Admin admin) {
        ModelAndView addAdminMV = new ModelAndView("admin/crud-result");
        AdminDTO adminDTO = adminService.add(admin);
        addAdminMV.addObject(adminDTO);
        return addAdminMV;
    }

    @GetMapping("/by-id")
    public ModelAndView getById(AdminDTO adminDTO) {
        ModelAndView getAdminMV = new ModelAndView("admin/crud-result");
        AdminDTO databaseAdminDTO = adminService.getById(adminDTO.getId());
        getAdminMV.addObject(databaseAdminDTO);
        return getAdminMV;
    }

    @GetMapping("/by-email")
    public ModelAndView getByEmail(AdminDTO adminDTO) {
        ModelAndView getAdminMV = new ModelAndView("admin/crud-result");
        AdminDTO databaseAdminDTO = adminService.getByEmail(adminDTO.getEmail());
        getAdminMV.addObject(databaseAdminDTO);
        return getAdminMV;
    }

    @GetMapping("/update-form")
    public ModelAndView showUpdateForm(@Valid AdminCredentialsForm adminCredentialsForm) {
        ModelAndView adminUpdateMV = new ModelAndView("admin/update-form");
        Admin admin = adminService.getByCredentials(adminCredentialsForm.getEmail(), adminCredentialsForm.getPassword());
        adminUpdateMV.addObject(admin);
        return adminUpdateMV;
    }

    @PostMapping("/updated-admin")
    public ModelAndView update(@Valid Admin admin) {
        ModelAndView adminUpdateResultMV = new ModelAndView("admin/crud-result");
        AdminDTO adminDTO = adminService.update(admin);
        adminUpdateResultMV.addObject(adminDTO);
        return adminUpdateResultMV;
    }

    @PostMapping("/deleted-admin")
    public ModelAndView delete(@Valid AdminCredentialsForm adminCredentialsForm) {
        ModelAndView adminDeleteMV = new ModelAndView("admin/crud-result");
        AdminDTO adminDTO = adminService.deleteByCredentials(adminCredentialsForm.getEmail(), adminCredentialsForm.getPassword());
        adminDeleteMV.addObject(adminDTO);
        return adminDeleteMV;
    }
}
