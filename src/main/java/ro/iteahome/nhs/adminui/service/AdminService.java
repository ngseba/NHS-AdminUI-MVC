package ro.iteahome.nhs.adminui.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.iteahome.nhs.adminui.model.dto.AdminDTO;
import ro.iteahome.nhs.adminui.model.entity.Admin;

@Service
public class AdminService {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ModelMapper modelMapper;

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    private final String ADMINS_URL = "https://nhsbackend.myserverapps.com/admins";

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
}
