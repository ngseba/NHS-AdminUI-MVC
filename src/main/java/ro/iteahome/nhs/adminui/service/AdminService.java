package ro.iteahome.nhs.adminui.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.iteahome.nhs.adminui.model.dto.AdminDTO;
import ro.iteahome.nhs.adminui.model.entity.Admin;
import ro.iteahome.nhs.adminui.model.form.AdminCredentialsForm;

@Service
public class AdminService {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ModelMapper modelMapper;

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    //    private final String ADMINS_URL = "https://nhsbackendstage.myserverapps.com/admins";
    private final String ADMINS_URL = "http://localhost:8080/admins";

    public AdminDTO add(Admin admin) {
        return restTemplate.postForObject(ADMINS_URL, admin, AdminDTO.class);
    }

    public AdminDTO getById(int id) {
        return restTemplate.getForObject(
                ADMINS_URL
                        .concat("/by-id/")
                        .concat(String.valueOf(id)),
                AdminDTO.class);
    }

    public AdminDTO getByEmail(String email) {
        return restTemplate.getForObject(
                ADMINS_URL
                        .concat("/by-email/")
                        .concat(email),
                AdminDTO.class);
    }

    public Admin getByCredentials(String email, String password) {
        return restTemplate.getForObject(
                ADMINS_URL
                        .concat("/by-credentials/?email=")
                        .concat(email)
                        .concat("&password=")
                        .concat(password),
                Admin.class);
    }

    public AdminDTO update(Admin admin) {
        restTemplate.put(ADMINS_URL, admin);
        return restTemplate.getForObject(
                ADMINS_URL
                        .concat("/by-id/")
                        .concat(String.valueOf(admin.getId())),
                AdminDTO.class);
    }

    public AdminDTO deleteByCredentials(String email, String password) {
        Admin admin = getByCredentials(email, password);
        AdminDTO adminDTO = modelMapper.map(admin, AdminDTO.class);
        restTemplate.delete(
                ADMINS_URL
                        .concat("/by-credentials/?email=")
                        .concat(email)
                        .concat("&password=")
                        .concat(password));
        return adminDTO;
    }
}
