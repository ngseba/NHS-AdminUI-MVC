package ro.iteahome.nhs.adminui.controller;

import jdk.swing.interop.SwingInterOpUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.iteahome.nhs.adminui.model.entity.Nurse;
import ro.iteahome.nhs.adminui.service.NurseService;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("/nurses")
public class NurseController {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private NurseService nurseService;

    @Autowired
    private ModelMapper modelMapper;

// LINK "GET" REQUESTS: ------------------------------------------------------------------------------------------------



    @GetMapping("/add-form")
    public ModelAndView showAddForm(Nurse nurse) {
        ArrayList specialties = (ArrayList) nurseService.getSpecialties();
        ArrayList titles = (ArrayList) nurseService.getTitles();
        return new ModelAndView("nurse/add-form")
        .addObject("nurseSpecialties",specialties).addObject("nurseTitles",titles);
    }

    @GetMapping("/get-form")
    public String showGetForm(Nurse nurse) { return "nurse/get-form"; }

    @GetMapping("/update-search-form")
    public String showUpdateSearchForm(Nurse nurse) {
        return "nurse/update-search-form";
    }

    @GetMapping("/delete-form")
    public String showDeleteForm(Nurse nurse) {
        return "nurse/delete-form";
    }

// METHODS: ------------------------------------------------------------------------------------------------------------

    // TODO: Incorporate exception handling. Leaving form fields empty is an issue.

    @PostMapping
    public ModelAndView add(@Valid Nurse nurse) {
        Nurse databaseNurse = nurseService.add(nurse);
        return new ModelAndView("nurse/home-nurse").addObject(databaseNurse);
    }

    @GetMapping("/by-email")
    public ModelAndView getByEmail( Nurse nurse) {
        Nurse databaseNurse = nurseService.findByEmail(nurse.getEmail());
        return new ModelAndView("nurse/home-nurse").addObject(databaseNurse);
    }


    @GetMapping("/update-form-by-email")
    public ModelAndView showUpdateFormByEmail(Nurse nurse) {
        ArrayList specialties = (ArrayList) nurseService.getSpecialties();
        ArrayList titles = (ArrayList) nurseService.getTitles();
        Nurse databaseNurse = nurseService.findByEmail(nurse.getEmail());
        return new ModelAndView("nurse/update-form").addObject(databaseNurse)
        .addObject("nurseSpecialties",specialties).addObject("nurseTitles",titles);
    }

    @PostMapping("/updated-nurse")
    public ModelAndView update(@Valid Nurse nurse) {
        Nurse databaseNurse = nurseService.update(nurse);
        return new ModelAndView("nurse/home-nurse").addObject(databaseNurse);
    }

    @PostMapping("/delete-by-email")
    public ModelAndView deleteByEmail(Nurse nurse) {
        Nurse databaseNurse = nurseService.findByEmail(nurse.getEmail());
        nurseService.deleteByEmail(databaseNurse.getEmail());
        return new ModelAndView("nurse/home-nurse").addObject(databaseNurse);
    }

}