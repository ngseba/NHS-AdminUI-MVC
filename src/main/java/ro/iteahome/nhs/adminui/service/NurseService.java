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
import ro.iteahome.nhs.adminui.model.entity.Nurse;

import java.util.Base64;

@Service
public class NurseService {
    // DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private RestTemplate restTemplate;

// FIELDS: -------------------------------------------------------------------------------------------------------------

    private final String CREDENTIALS = "NHS_ADMIN_UI:P@ssW0rd!";
    private final String ENCODED_CREDENTIALS = new String(Base64.getEncoder().encode(CREDENTIALS.getBytes()));
    private final String NURSES_URL = RestUrlConfig.SERVER_ROOT_URL + "/nurses";


// AUTHENTICATION FOR REST REQUESTS: -----------------------------------------------------------------------------------

    private HttpHeaders getAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + ENCODED_CREDENTIALS);
        return headers;
    }

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    public Nurse add(Nurse Nurse) {
        ResponseEntity<Nurse> nurseResponse =
                restTemplate.exchange(
                        NURSES_URL,
                        HttpMethod.POST,
                        new HttpEntity<>(Nurse, getAuthHeaders()),
                        Nurse.class);
        return nurseResponse.getBody();
    }


    public Object getSpecialties(){
        ResponseEntity<Object> nurseResponse =
                restTemplate.exchange(
                        NURSES_URL + "/retrieve-nurse-specialty",
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        Object.class);

        return nurseResponse.getBody();
    }

    public Object getTitles(){
        ResponseEntity<Object> nurseResponse =
                restTemplate.exchange(
                        NURSES_URL + "/retrieve-nurse-title",
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        Object.class);

        return nurseResponse.getBody();
    }


    public boolean existsByCnp(String cnp,String licenseNo) {
        ResponseEntity<Boolean> nurseExists =
                restTemplate.exchange(
                        NURSES_URL + "/existence/by-cnp?cnp="+cnp,
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        Boolean.class);
        return nurseExists.getBody();
    }


    public Nurse findByCnp(String Cnp) {
        ResponseEntity<Nurse> nurseResponse =
                restTemplate.exchange(
                        NURSES_URL + "/find-by-cnp/?cnp=" + Cnp,
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        Nurse.class);
        Nurse nurseDTO = nurseResponse.getBody();
        if (nurseDTO != null) {
            return nurseDTO;
        } else {
            throw new GlobalNotFoundException("NURSES");
        }
    }

    public Nurse update(Nurse newNurse) {
        Nurse nurseDTO = findByCnp(newNurse.getCnp());
        if (nurseDTO != null) {
            restTemplate.exchange(
                    NURSES_URL,
                    HttpMethod.PUT,
                    new HttpEntity<>(newNurse, getAuthHeaders()),
                    Nurse.class);
            return findByCnp(nurseDTO.getCnp());
        } else {
            throw new GlobalNotFoundException("NURSES");
        }
    }

    public Nurse deleteByCnp(String Cnp) {
        Nurse nurseDTO = findByCnp(Cnp);
        if (nurseDTO != null) {
            ResponseEntity<Nurse> nurseResponse =
                    restTemplate.exchange(
                            NURSES_URL + "/delete/by-cnp/?cnp=" + Cnp,
                            HttpMethod.DELETE,
                            new HttpEntity<>(getAuthHeaders()),
                            Nurse.class);
            return nurseResponse.getBody();
        } else {
            throw new GlobalNotFoundException("NURSE");
        }
    }

}