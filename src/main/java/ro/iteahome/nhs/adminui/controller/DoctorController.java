package ro.iteahome.nhs.adminui.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.iteahome.nhs.adminui.model.entity.Doctor;
import ro.iteahome.nhs.adminui.service.DoctorService;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ModelMapper modelMapper;

// LINK "GET" REQUESTS: ------------------------------------------------------------------------------------------------



    @GetMapping("/add-form")
    public ModelAndView showAddForm(Doctor doctor) {
        ArrayList<String> specialties = (ArrayList<String>) doctorService.getSpecialties();
        ArrayList<String> titles = (ArrayList<String>) doctorService.getTitles();
        return new ModelAndView("doctor/add-form")
        .addObject("doctorSpecialties",specialties).addObject("doctorTitles",titles);
    }

    @GetMapping("/get-form")
    public String showGetForm(Doctor doctor) { return "doctor/get-form"; }

    @GetMapping("/update-search-form")
    public String showUpdateSearchForm(Doctor doctor) {
        return "doctor/update-search-form";
    }

    @GetMapping("/delete-form")
    public String showDeleteForm(Doctor doctor) {
        return "doctor/delete-form";
    }

// METHODS: ------------------------------------------------------------------------------------------------------------

    // TODO: Incorporate exception handling. Leaving form fields empty is an issue.

    @PostMapping
    public ModelAndView add(@Valid Doctor doctor) {
        Doctor databaseDoctor = doctorService.add(doctor);
        return new ModelAndView("doctor/home-doctor").addObject(databaseDoctor);
    }


    @GetMapping("/by-email")
    public ModelAndView getByEmail( Doctor doctor) {
        Doctor databaseDoctor = doctorService.findByEmail(doctor.getEmail());
        return new ModelAndView("doctor/home-doctor").addObject(databaseDoctor);
    }


    @GetMapping("/by-cnp")
    public ModelAndView getByCnp( Doctor doctor) {
        Doctor databaseDoctor = doctorService.findByCnp(doctor.getCnp());
        return new ModelAndView("doctor/home-doctor").addObject(databaseDoctor);
    }

    @GetMapping("/update-form-by-email")
    public ModelAndView showUpdateFormByEmail(Doctor doctor) {
        ArrayList specialties = (ArrayList) doctorService.getSpecialties();
        ArrayList titles = (ArrayList) doctorService.getTitles();
        Doctor databaseDoctor = doctorService.findByEmail(doctor.getEmail());
        return new ModelAndView("doctor/update-form").addObject(databaseDoctor)
        .addObject("doctorSpecialties",specialties).addObject("doctorTitles",titles);
    }

    @GetMapping("/update-form-by-cnp")
    public ModelAndView showUpdateFormByCnp(Doctor doctor) {
        ArrayList specialties = (ArrayList) doctorService.getSpecialties();
        ArrayList titles = (ArrayList) doctorService.getTitles();
        Doctor databaseDoctor = doctorService.findByCnp(doctor.getCnp());
        return new ModelAndView("doctor/update-form").addObject(databaseDoctor)
                .addObject("doctorSpecialties",specialties).addObject("doctorTitles",titles);
    }

    @PostMapping("/updated-doctor")
    public ModelAndView update(@Valid Doctor doctor) {
        Doctor databaseDoctor = doctorService.update(doctor);
        return new ModelAndView("doctor/home-doctor").addObject(databaseDoctor);
    }


    @PostMapping("/delete-by-email")
    public ModelAndView deleteByEmail(Doctor doctor) {
        Doctor databaseDoctor = doctorService.findByEmail(doctor.getEmail());
        doctorService.deleteByEmail(databaseDoctor.getEmail());

        return new ModelAndView("doctor/home-doctor").addObject(databaseDoctor);
    }

    @PostMapping("/delete-by-cnp")
    public ModelAndView deleteByCnp(Doctor doctor) {
        Doctor databaseDoctor = doctorService.findByCnp(doctor.getCnp());
        doctorService.deleteByCnp(databaseDoctor.getCnp());

        return new ModelAndView("doctor/home-doctor").addObject(databaseDoctor);
    }

}