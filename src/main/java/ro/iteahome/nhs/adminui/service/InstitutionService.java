package ro.iteahome.nhs.adminui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.iteahome.nhs.adminui.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.adminui.model.entity.Institution;

import java.util.Base64;

@Service
public class InstitutionService {
    // DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private RestTemplate restTemplate;

// FIELDS: -------------------------------------------------------------------------------------------------------------

    private final String CREDENTIALS = "NHS_ADMIN_UI:P@ssW0rd!";
    private final String ENCODED_CREDENTIALS = new String(Base64.getEncoder().encode(CREDENTIALS.getBytes()));
    //private final String INSTITUTIONS_URL = "http://nhsbackendstage.myserverapps.com/medical-institutions";
    private final String INSTITUTIONS_URL = "http://localhost:8081/medical-institutions";

// AUTHENTICATION FOR REST REQUESTS: -----------------------------------------------------------------------------------

    private HttpHeaders getAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + ENCODED_CREDENTIALS);
        return headers;
    }

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    public Institution add(Institution institution) {
        ResponseEntity<Institution> institutionResponse =
                restTemplate.exchange(
                        INSTITUTIONS_URL,
                        HttpMethod.POST,
                        new HttpEntity<>(institution, getAuthHeaders()),
                        Institution.class);
        return institutionResponse.getBody();
    }

    public Institution findById(int id) {
        ResponseEntity<Institution> institutionResponse =
                restTemplate.exchange(
                        INSTITUTIONS_URL + "/by-id/" + id,
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        Institution.class);
        Institution institution = institutionResponse.getBody();
        if (institution != null) {
            return institution;
        } else {
            throw new GlobalNotFoundException("INSTITUTION");
        }
    }

    public Institution findByCui(String Cui) {
        ResponseEntity<Institution> instituionResponse =
                restTemplate.exchange(
                        INSTITUTIONS_URL + "/by-cui/" + Cui,
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        Institution.class);
        Institution institution = instituionResponse.getBody();
        if (institution != null) {
            return institution;
        } else {
            throw new GlobalNotFoundException("INSTITUTION");
        }
    }

    public Institution update(Institution newInstitution) {
        Institution institution = findById(newInstitution.getId());
        if (institution != null) {
            restTemplate.exchange(
                    INSTITUTIONS_URL,
                    HttpMethod.PUT,
                    new HttpEntity<>(newInstitution, getAuthHeaders()),
                    Institution.class);
            return findById(newInstitution.getId());
        } else {
            throw new GlobalNotFoundException("INSTITUTION");
        }
    }

    public Institution deleteById(int id) {
        Institution institution = findById(id);
        if (institution != null) {
            ResponseEntity<Institution> institutionResponse =
                    restTemplate.exchange(
                            INSTITUTIONS_URL + "/by-id/" + id,
                            HttpMethod.DELETE,
                            new HttpEntity<>(getAuthHeaders()),
                            Institution.class);
            return institutionResponse.getBody();
        } else {
            throw new GlobalNotFoundException("INSTITUTION");
        }
    }

    public Institution deleteByCui(String Cui) {
        Institution institution = findByCui(Cui);
        if (institution != null) {
            ResponseEntity<Institution> institutionResponse =
                    restTemplate.exchange(
                            INSTITUTIONS_URL + "/by-cui/" + Cui,
                            HttpMethod.DELETE,
                            new HttpEntity<>(getAuthHeaders()),
                            Institution.class);
            return institutionResponse.getBody();
        } else {
            throw new GlobalNotFoundException("INSTITUTIONS");
        }
    }

}
