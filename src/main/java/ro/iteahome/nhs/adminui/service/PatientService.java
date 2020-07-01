package ro.iteahome.nhs.adminui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.iteahome.nhs.adminui.config.rest.RestConfig;
import ro.iteahome.nhs.adminui.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.adminui.model.entity.Patient;

@Service
public class PatientService {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestConfig restConfig;

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    public Patient add(Patient patient) {
        ResponseEntity<Patient> patientResponse =
                restTemplate.exchange(
                        restConfig.getSERVER_URL() + restConfig.getPATIENTS_URI(),
                        HttpMethod.POST,
                        new HttpEntity<>(patient, restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                        Patient.class);
        return patientResponse.getBody();
    }

    public Patient findByCnp(String cnp) {
        ResponseEntity<Patient> patientResponse =
                restTemplate.exchange(
                        restConfig.getSERVER_URL() + restConfig.getPATIENTS_URI() + "/by-cnp/" + cnp,
                        HttpMethod.GET,
                        new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                        Patient.class);
        Patient patientDTO = patientResponse.getBody();
        if (patientDTO != null) {
            return patientDTO;
        } else {
            throw new GlobalNotFoundException("PATIENTS");
        }
    }

    public Patient update(Patient newPatient) {
        Patient patientDTO = findByCnp(newPatient.getCnp());
        if (patientDTO != null) {
            restTemplate.exchange(
                    restConfig.getSERVER_URL() + restConfig.getPATIENTS_URI(),
                    HttpMethod.PUT,
                    new HttpEntity<>(newPatient, restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                    Patient.class);
            return findByCnp(patientDTO.getCnp());
        } else {
            throw new GlobalNotFoundException("PATIENTS");
        }
    }

    public Patient deleteByCnp(String cnp) {
        Patient patientDTO = findByCnp(cnp);
        if (patientDTO != null) {
            ResponseEntity<Patient> patientResponse =
                    restTemplate.exchange(
                            restConfig.getSERVER_URL() + restConfig.getPATIENTS_URI() + "/by-cnp/" + cnp,
                            HttpMethod.DELETE,
                            new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                            Patient.class);
            return patientResponse.getBody();
        } else {
            throw new GlobalNotFoundException("PATIENTS");
        }
    }
}
