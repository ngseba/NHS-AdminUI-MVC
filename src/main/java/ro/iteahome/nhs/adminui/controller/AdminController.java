package ro.iteahome.nhs.adminui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
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

    @GetMapping("/admin-add-form")
    public String showAdminAddForm(Admin admin) {
        return "admin/admin-add-form";
    }

    @GetMapping("/admin-get-form")
    public String showAdminGetForm(AdminDTO adminDTO) {
        return "admin/admin-get-form";
    }

// METHODS: ------------------------------------------------------------------------------------------------------------

    @PostMapping
    public ModelAndView add(@Valid Admin admin) {
        ModelAndView addAdminMV = new ModelAndView("admin/admin-crud-result");
        AdminDTO adminDTO = adminService.add(admin);
        addAdminMV.addObject(adminDTO);
        return addAdminMV;
    }

    @GetMapping("/by-id")
    public ModelAndView getById(AdminDTO adminDTO) {
        ModelAndView getAdminMV = new ModelAndView("admin/admin-crud-result");
        AdminDTO databaseAdminDTO = adminService.getById(adminDTO.getId());
        getAdminMV.addObject(databaseAdminDTO);
        return getAdminMV;
    }

    @GetMapping("/by-email")
    public ModelAndView getByEmail(AdminDTO adminDTO) {
        ModelAndView getAdminMV = new ModelAndView("admin/admin-crud-result");
        AdminDTO databaseAdminDTO = adminService.getByEmail(adminDTO.getEmail());
        getAdminMV.addObject(databaseAdminDTO);
        return getAdminMV;
    }
}
