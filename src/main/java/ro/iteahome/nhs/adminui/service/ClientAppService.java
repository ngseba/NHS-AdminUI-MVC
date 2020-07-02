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
import ro.iteahome.nhs.adminui.model.dto.ClientAppCredentials;
import ro.iteahome.nhs.adminui.model.dto.ClientAppDTO;
import ro.iteahome.nhs.adminui.model.dto.RoleCreationDTO;
import ro.iteahome.nhs.adminui.model.dto.RoleDTO;
import ro.iteahome.nhs.adminui.model.entity.ClientApp;
import ro.iteahome.nhs.adminui.model.entity.Role;

import javax.validation.Valid;
import java.util.Base64;

@Service
public class ClientAppService {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private RestTemplate restTemplate;

// FIELDS: -------------------------------------------------------------------------------------------------------------

    private final String CREDENTIALS = "NHS_ADMIN_UI:P@ssW0rd!";
    private final String ENCODED_CREDENTIALS = new String(Base64.getEncoder().encode(CREDENTIALS.getBytes()));
    private final String CLIENT_APPS_URL = RestUrlConfig.SERVER_ROOT_URL + "/client-apps";

// AUTHENTICATION FOR REST REQUESTS: -----------------------------------------------------------------------------------

    private HttpHeaders getAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + ENCODED_CREDENTIALS);
        return headers;
    }

// C.R.U.D. METHODS: ---------------------------------------------------------------------------------------------------

    public ClientApp add(ClientAppDTO clientAppDTO, int roleId) {
        ClientAppCredentials clientAppCredentials = new ClientAppCredentials(
                clientAppDTO.getName(),
                clientAppDTO.getPassword()
        );
        ResponseEntity<ClientApp> clientAppResponse =
                restTemplate.exchange(
                        CLIENT_APPS_URL + "/with-role-id/" + roleId,
                        HttpMethod.POST,
                        new HttpEntity<>(clientAppCredentials,getAuthHeaders()),
                        ClientApp.class);
        return clientAppResponse.getBody();
    }

    public ClientApp findById(int id) {
        ResponseEntity<ClientApp> clientAppResponse =
                restTemplate.exchange(
                        CLIENT_APPS_URL + "/by-id/" + id,
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        ClientApp.class);
        ClientApp clientApp = clientAppResponse.getBody();
        if (clientApp != null) {
            return clientApp;
        } else {
            throw new GlobalNotFoundException("CLIENT APP");
        }
    }

    public ClientApp findByName(String name) {
        ResponseEntity<ClientApp> clientAppResponse =
                restTemplate.exchange(
                        CLIENT_APPS_URL + "/by-name/" + name,
                        HttpMethod.GET,
                        new HttpEntity<>(getAuthHeaders()),
                        ClientApp.class);
        ClientApp clientApp = clientAppResponse.getBody();
        if (clientApp != null) {
            return clientApp;
        } else {
            throw new GlobalNotFoundException("CLIENT APP");
        }
    }

    public ClientApp update(@Valid ClientApp clientApp) {
        ClientApp databaseClientApp = findById(clientApp.getId());
        if (databaseClientApp != null) {
            ResponseEntity<ClientApp> clientAppResponse =
                    restTemplate.exchange(
                            CLIENT_APPS_URL,
                            HttpMethod.PUT,
                            new HttpEntity<>(clientApp, getAuthHeaders()),
                            ClientApp.class);
            return clientAppResponse.getBody();
        } else {
            throw new GlobalNotFoundException("CLIENT APP");
        }
    }

    public ClientApp updateRole(ClientApp clientApp, int roleId) {
        ClientApp databaseClientApp = findById(clientApp.getId());
        if (databaseClientApp != null) {
            ResponseEntity<ClientApp> clientAppResponse =
                    restTemplate.exchange(
                            CLIENT_APPS_URL +"/role/?clientAppId=" + clientApp.getId()+ "&roleId=" + roleId,
                            HttpMethod.PUT,
                            new HttpEntity<>(clientApp, getAuthHeaders()),
                            ClientApp.class);
            return clientAppResponse.getBody();
        } else {
            throw new GlobalNotFoundException("CLIENT APP");
        }
    }

    public ClientApp deleteByName(String name) {
        ClientApp databaseClientApp = findByName(name);
        if (databaseClientApp != null) {
            ResponseEntity<ClientApp> clientAppResponse =
                    restTemplate.exchange(
                            CLIENT_APPS_URL + "/by-name/" + name,
                            HttpMethod.DELETE,
                            new HttpEntity<>(getAuthHeaders()),
                            ClientApp.class);
            return clientAppResponse.getBody();
        } else {
            throw new GlobalNotFoundException("CLIENT APP");
        }
    }
}
