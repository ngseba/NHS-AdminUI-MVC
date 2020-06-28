package ro.iteahome.nhs.adminui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.iteahome.nhs.adminui.config.RestUrlConfig;
import ro.iteahome.nhs.adminui.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.adminui.model.entity.Doctor;

import java.util.Base64;

@Service
public class DoctorService {
    // DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private RestTemplate restTemplate;

// FIELDS: -------------------------------------------------------------------------------------------------------------

    private final String CREDENTIALS = "NHS_ADMIN_UI:P@ssW0rd!";
    private final String ENCODED_CREDENTIALS = new String(Base64.getEncoder().encode(CREDENTIALS.getBytes()));
    private final String DOCTORS_URL = RestUrlConfig.SERVER_ROOT_URL + "/doctors";


// AUTHENTICATION FOR REST REQUESTS: -----------------------------------------------------------------------------------

    private HttpHeaders getAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + ENCODED_CREDENTIALS);
        return headers;
    }

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    public Doctor add(Doctor doctor) {
        ResponseEntity<Doctor> doctorResponse =
                restTemplate.exchange(
                        DOCTORS_URL,
                        HttpMethod.POST,
                        new HttpEntity<>(doctor, getAuthHeaders()),
                        Doctor.class);
        return doctorResponse.getBody();
    }

    public String[] getSpecialties(){
        ResponseEntity<String[]> doctorResponse =
                restTemplate.exchange(
                        DOCTORS_URL + "/specialty",
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        String[].class);

        return doctorResponse.getBody();
    }

    public String[] getTitles(){
        ResponseEntity<String[]> doctorResponse =
                restTemplate.exchange(
                        DOCTORS_URL + "/title",
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        String[].class);

        return doctorResponse.getBody();
    }


    public boolean existsByCnp(String cnp) {
        ResponseEntity<Boolean> doctorExists =
                restTemplate.exchange(
                DOCTORS_URL + "/existence/by-cnp/" + cnp,
                HttpMethod.GET,
                new HttpEntity<>(getAuthHeaders()),
                Boolean.class);
        return doctorExists.getBody();
    }


    public Doctor findByCnp(String Cnp) {
        ResponseEntity<Doctor> doctorResponse =
                restTemplate.exchange(
                        DOCTORS_URL + "/by-cnp/" + Cnp,
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        Doctor.class);
        Doctor doctorDTO = doctorResponse.getBody();
        if (doctorDTO != null) {
            return doctorDTO;
        } else {
            throw new GlobalNotFoundException("DOCTORS");
        }
    }

    public Doctor update(Doctor newDoctor) {
        Doctor doctorDTO = findByCnp(newDoctor.getCnp());
        if (doctorDTO != null) {
            restTemplate.exchange(
                    DOCTORS_URL,
                    HttpMethod.PUT,
                    new HttpEntity<>(newDoctor, getAuthHeaders()),
                    Doctor.class);
            return findByCnp(doctorDTO.getCnp());
        } else {
            throw new GlobalNotFoundException("DOCTOR");
        }
    }


    public Doctor deleteByCnp(String Cnp) {
        Doctor doctorDTO = findByCnp(Cnp);
        if (doctorDTO != null) {
            ResponseEntity<Doctor> doctorResponse =
                    restTemplate.exchange(
                            DOCTORS_URL + "/by-cnp/" + Cnp,
                            HttpMethod.DELETE,
                            new HttpEntity<>(getAuthHeaders()),
                            Doctor.class);
            return doctorResponse.getBody();
        } else {
            throw new GlobalNotFoundException("DOCTOR");
        }
    }

}