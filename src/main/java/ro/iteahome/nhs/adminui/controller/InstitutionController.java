package ro.iteahome.nhs.adminui.controller;

import jdk.swing.interop.SwingInterOpUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.iteahome.nhs.adminui.model.entity.Institution;
import ro.iteahome.nhs.adminui.service.InstitutionService;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("/medical-institutions")
public class InstitutionController {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private ModelMapper modelMapper;

// LINK "GET" REQUESTS: ------------------------------------------------------------------------------------------------



    @GetMapping("/add-form")
    public ModelAndView showAddForm(Institution institution) {
        ArrayList types = (ArrayList) institutionService.getInstitutionType();
        return new ModelAndView("institution/add-form").addObject("types",types);
    }

    @GetMapping("/get-form")
    public String showGetForm(Institution institution) { return "institution/get-form"; }

    @GetMapping("/update-search-form")
    public String showUpdateSearchForm(Institution institution) {
        return "institution/update-search-form";
    }

    @GetMapping("/delete-form")
    public String showDeleteForm(Institution institution) {
        return "institution/delete-form";
    }

// METHODS: ------------------------------------------------------------------------------------------------------------

    // TODO: Incorporate exception handling. Leaving form fields empty is an issue.

    @PostMapping
    public ModelAndView add(@Valid Institution institution) {
        Institution databaseInstitution = institutionService.add(institution);
        return new ModelAndView("institution/home-institution").addObject(databaseInstitution);
    }

    @GetMapping("/by-id")
    public ModelAndView getById( Institution institution) {
        Institution databaseInstitution = institutionService.findById(institution.getId());
        return new ModelAndView("institution/home-institution").addObject(databaseInstitution);
    }

    @GetMapping("/by-cui")
    public ModelAndView getByCui( Institution institution) {
        Institution databaseInstitution = institutionService.findByCui(institution.getCui());
        return new ModelAndView("institution/home-institution").addObject(databaseInstitution);
    }

    @GetMapping("/update-form-by-id")
    public ModelAndView showUpdateFormById(Institution institution) {
        ArrayList types = (ArrayList) institutionService.getInstitutionType();
        Institution databaseInstitution = institutionService.findById(institution.getId());
        return new ModelAndView("institution/update-form").addObject(databaseInstitution).addObject("types",types);
    }

    @GetMapping("/update-form-by-cui")
    public ModelAndView showUpdateFormByCui(Institution institution) {
        ArrayList types = (ArrayList) institutionService.getInstitutionType();
        Institution databaseInstitution = institutionService.findByCui(institution.getCui());
        return new ModelAndView("institution/update-form").addObject(databaseInstitution).addObject("types",types);
    }

    @PostMapping("/updated-institution")
    public ModelAndView update(@Valid Institution institution) {
        Institution databaseInstitution = institutionService.update(institution);
        return new ModelAndView("institution/home-institution").addObject(databaseInstitution);
    }

    @PostMapping("/delete-by-id")
    public ModelAndView deleteById(Institution institution) {
        Institution databaseInstitution = institutionService.findById(institution.getId());
        institutionService.deleteById(institution.getId());
        return new ModelAndView("institution/home-institution").addObject(databaseInstitution);
    }

    @PostMapping("/delete-by-cui")
    public ModelAndView deleteByCui(Institution institution) {
        Institution databaseInstitution = institutionService.findByCui(institution.getCui());
        institutionService.deleteByCui(institution.getCui());

        return new ModelAndView("institution/home-institution").addObject(databaseInstitution);
    }

}
