package ro.iteahome.nhs.adminui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.iteahome.nhs.adminui.exception.business.RoleNotFoundException;
import ro.iteahome.nhs.adminui.model.dto.RoleDTO;
import ro.iteahome.nhs.adminui.model.entity.Role;

import java.util.Base64;

@Service
public class RoleService implements UserDetailsService {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private RestTemplate restTemplate;

// FIELDS: -------------------------------------------------------------------------------------------------------------

    private final String CREDENTIALS = "NHS_ADMIN_UI:P@ssW0rd!";
    private final String ENCODED_CREDENTIALS = new String(Base64.getEncoder().encode(CREDENTIALS.getBytes()));
    private final String ROLES_URL = "https://nhsbackendstage.myserverapps.com/roles";

// AUTHENTICATION FOR REST REQUESTS: -----------------------------------------------------------------------------------

    private HttpHeaders getAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + ENCODED_CREDENTIALS);
        return headers;
    }

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    public Role add(RoleDTO roleDTO) {
        ResponseEntity<Role> roleResponse =
                restTemplate.exchange(
                        ROLES_URL,
                        HttpMethod.POST,
                        new HttpEntity<>(roleDTO, getAuthHeaders()),
                        Role.class);
        return roleResponse.getBody();
    }

    public Role findById(int id) {
        ResponseEntity<Role> roleResponse =
                restTemplate.exchange(
                        ROLES_URL + "/by-id/" + id,
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        Role.class);
        Role role = roleResponse.getBody();
        if (role != null) {
            return role;
        } else {
            throw new RoleNotFoundException();
        }
    }

    public Role findByName(String name) {
        ResponseEntity<Role> roleResponse =
                restTemplate.exchange(
                        ROLES_URL + "/by-name/" + name,
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        Role.class);
        Role role = roleResponse.getBody();
        if (role != null) {
            return role;
        } else {
            throw new RoleNotFoundException();
        }
    }

    public Role update(Role newRole) {
        Role role = findById(newRole.getId());
        if (role != null) {
            restTemplate.exchange(
                    ROLES_URL,
                    HttpMethod.PUT,
                    new HttpEntity<>(newRole, getAuthHeaders()),
                    Role.class);
            return findById(newRole.getId());
        } else {
            throw new RoleNotFoundException();
        }
    }

    public Role deleteById(int id) {
        Role role = findById(id);
        if (role != null) {
            ResponseEntity<Role> roleResponse =
                    restTemplate.exchange(
                            ROLES_URL + "/by-id/?id=" + id,
                            HttpMethod.DELETE,
                            new HttpEntity<>(getAuthHeaders()),
                            Role.class);
            return roleResponse.getBody();
        } else {
            throw new RoleNotFoundException();
        }
    }

    public Role deleteByName(String name) {
        Role role = findByName(name);
        if (role != null) {
            ResponseEntity<Role> roleResponse =
                    restTemplate.exchange(
                            ROLES_URL + "/by-id/?id=" + name,
                            HttpMethod.DELETE,
                            new HttpEntity<>(getAuthHeaders()),
                            Role.class);
            return roleResponse.getBody();
        } else {
            throw new RoleNotFoundException();
        }
    }

// OVERRIDDEN "UserDetailsService" METHODS: ----------------------------------------------------------------------------

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserDetails roleDetails =
                restTemplate.exchange(
                        ROLES_URL + "/by-name/" + name,
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        UserDetails.class)
                        .getBody();
        if (roleDetails != null) {
            return roleDetails;
        } else {
            throw new UsernameNotFoundException(name);
        }
    }
}
