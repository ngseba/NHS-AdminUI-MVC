package ro.iteahome.nhs.adminui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.iteahome.nhs.adminui.config.rest.RestConfig;
import ro.iteahome.nhs.adminui.exception.business.GlobalNotFoundException;
import ro.iteahome.nhs.adminui.model.entity.Nurse;

@Service
public class NurseService {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestConfig restConfig;

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    public Nurse add(Nurse nurse) {
        ResponseEntity<Nurse> nurseResponse =
                restTemplate.exchange(
                        restConfig.getSERVER_URL() + restConfig.getNURSES_URI(),
                        HttpMethod.POST,
                        new HttpEntity<>(nurse, restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                        Nurse.class);
        return nurseResponse.getBody();
    }

    public String[] getSpecialties() {
        ResponseEntity<String[]> nurseResponse =
                restTemplate.exchange(
                        restConfig.getSERVER_URL() + restConfig.getNURSES_URI() + "/specialty",
                        HttpMethod.GET,
                        new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                        String[].class);

        return nurseResponse.getBody();
    }

    public String[] getTitles() {
        ResponseEntity<String[]> nurseResponse =
                restTemplate.exchange(
                        restConfig.getSERVER_URL() + restConfig.getNURSES_URI() + "/title",
                        HttpMethod.GET,
                        new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                        String[].class);

        return nurseResponse.getBody();
    }

    public boolean existsByCnp(String cnp, String licenseNo) {
        ResponseEntity<Boolean> nurseExists =
                restTemplate.exchange(
                        restConfig.getSERVER_URL() + restConfig.getNURSES_URI() + "/existence/by-cnp/" + cnp,
                        HttpMethod.GET,
                        new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                        Boolean.class);
        return nurseExists.getBody();
    }

    public Nurse findByCnp(String cnp) {
        ResponseEntity<Nurse> nurseResponse =
                restTemplate.exchange(
                        restConfig.getSERVER_URL() + restConfig.getNURSES_URI() + "/by-cnp/" + cnp,
                        HttpMethod.GET,
                        new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
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
                    restConfig.getSERVER_URL() + restConfig.getNURSES_URI(),
                    HttpMethod.PUT,
                    new HttpEntity<>(newNurse, restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                    Nurse.class);
            return findByCnp(nurseDTO.getCnp());
        } else {
            throw new GlobalNotFoundException("NURSES");
        }
    }

    public Nurse deleteByCnp(String cnp) {
        Nurse nurseDTO = findByCnp(cnp);
        if (nurseDTO != null) {
            ResponseEntity<Nurse> nurseResponse =
                    restTemplate.exchange(
                            restConfig.getSERVER_URL() + restConfig.getNURSES_URI() + "/by-cnp/" + cnp,
                            HttpMethod.DELETE,
                            new HttpEntity<>(restConfig.buildAuthHeaders(restConfig.getCREDENTIALS())),
                            Nurse.class);
            return nurseResponse.getBody();
        } else {
            throw new GlobalNotFoundException("NURSE");
        }
    }
}
