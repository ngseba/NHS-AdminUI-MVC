package ro.iteahome.nhs.adminui.controller;

import jdk.swing.interop.SwingInterOpUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.iteahome.nhs.adminui.model.entity.Nurse;
import ro.iteahome.nhs.adminui.model.entity.Patient;
import ro.iteahome.nhs.adminui.service.PatientService;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("/patients")
public class PatientController {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private PatientService patientService;

    @Autowired
    private ModelMapper modelMapper;

// LINK "GET" REQUESTS: ------------------------------------------------------------------------------------------------



    @GetMapping("/add-form")
    public ModelAndView showAddForm(Patient patient) {
        return new ModelAndView("patient/add-form");
    }

    @GetMapping("/get-form")
    public String showGetForm(Patient patient) { return "patient/get-form"; }

    @GetMapping("/update-search-form")
    public String showUpdateSearchForm(Patient patient) {
        return "patient/update-search-form";
    }

    @GetMapping("/delete-form")
    public String showDeleteForm(Patient patient) {
        return "patient/delete-form";
    }

// METHODS: ------------------------------------------------------------------------------------------------------------

    // TODO: Incorporate exception handling. Leaving form fields empty is an issue.

    @PostMapping
    public ModelAndView add(@Valid Patient patient) {
        Patient databasePatient = patientService.add(patient);
        return new ModelAndView("patient/home-patient").addObject(databasePatient);
    }

    @GetMapping("/by-id")
    public ModelAndView getById(Patient patient) {
        Patient databasePatient = patientService.findById(patient.getId());
        return new ModelAndView("patient/home-patient").addObject(databasePatient);
    }

    @GetMapping("/by-email")
    public ModelAndView getByCnp( Patient patient) {
        Patient databasePatient = patientService.findByCnp(patient.getCnp());
        return new ModelAndView("patient/home-patient").addObject(databasePatient);
    }

    @GetMapping("/update-form-by-id")
    public ModelAndView showUpdateFormById(Patient patient) {
        Patient databasePatient = patientService.findById(patient.getId());
        return new ModelAndView("patient/update-form").addObject(databasePatient);
    }

    @GetMapping("/update-form-by-cnp")
    public ModelAndView showUpdateFormByCnp(Patient patient) {
        Patient databasePatient = patientService.findByCnp(patient.getCnp());
        return new ModelAndView("patient/update-form").addObject(databasePatient);
    }

    @PostMapping("/updated-patient")
    public ModelAndView update(@Valid Patient patient) {
        Patient databasePatient = patientService.update(patient);
        return new ModelAndView("patient/home-patient").addObject(databasePatient);
    }

    @PostMapping("/delete-by-id")
    public ModelAndView deleteById(Patient patient) {
        Patient databasePatient = patientService.findById(patient.getId());
        patientService.deleteById(databasePatient.getId());
        return new ModelAndView("patient/home-patient").addObject(databasePatient);
    }

    @PostMapping("/delete-by-cnp")
    public ModelAndView deleteByCnp(Patient patient) {
        Patient databasePatient = patientService.findByCnp(patient.getEmail());
        patientService.deleteByCnp(databasePatient.getCnp());
        return new ModelAndView("patient/home-patient").addObject(databasePatient);
    }

}