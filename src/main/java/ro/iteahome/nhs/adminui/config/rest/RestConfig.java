package ro.iteahome.nhs.adminui.config.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class RestConfig {

// FIELDS: -------------------------------------------------------------------------------------------------------------

    @Value("${nhs.rest.server-url}")
    private String SERVER_URL;

    @Value("${nhs.rest.roles-uri}")
    private String ROLES_URI;

    @Value("${nhs.rest.admins-uri}")
    private String ADMINS_URI;

    @Value("${nhs.rest.client-apps-uri}")
    private String CLIENT_APPS_URI;

    @Value("${nhs.rest.institutions-uri}")
    private String INSTITUTIONS_URI;

    @Value("${nhs.rest.doctors-uri}")
    private String DOCTORS_URI;

    @Value("${nhs.rest.nurses-uri}")
    private String NURSES_URI;

    @Value("${nhs.rest.patients-uri}")
    private String PATIENTS_URI;

    @Value("${nhs.rest.credentials}")
    private String CREDENTIALS;

// Methods: ------------------------------------------------------------------------------------------------------------

    public HttpHeaders buildAuthHeaders(String credentials) {
        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.add(
                "Authorization",
                "Basic " + buildEncodedCredentials(credentials));
        return authHeaders;
    }

    public String buildEncodedCredentials(String credentials) {
        return new String(Base64.getEncoder().encode(credentials.getBytes()));
    }

// GETTERS AND SETTERS: ------------------------------------------------------------------------------------------------

    public String getSERVER_URL() {
        return SERVER_URL;
    }

    public void setSERVER_URL(String SERVER_URL) {
        this.SERVER_URL = SERVER_URL;
    }

    public String getROLES_URI() {
        return ROLES_URI;
    }

    public void setROLES_URI(String ROLES_URI) {
        this.ROLES_URI = ROLES_URI;
    }

    public String getADMINS_URI() {
        return ADMINS_URI;
    }

    public void setADMINS_URI(String ADMINS_URI) {
        this.ADMINS_URI = ADMINS_URI;
    }

    public String getCLIENT_APPS_URI() {
        return CLIENT_APPS_URI;
    }

    public void setCLIENT_APPS_URI(String CLIENT_APPS_URI) {
        this.CLIENT_APPS_URI = CLIENT_APPS_URI;
    }

    public String getINSTITUTIONS_URI() {
        return INSTITUTIONS_URI;
    }

    public void setINSTITUTIONS_URI(String INSTITUTIONS_URI) {
        this.INSTITUTIONS_URI = INSTITUTIONS_URI;
    }

    public String getDOCTORS_URI() {
        return DOCTORS_URI;
    }

    public void setDOCTORS_URI(String DOCTORS_URI) {
        this.DOCTORS_URI = DOCTORS_URI;
    }

    public String getNURSES_URI() {
        return NURSES_URI;
    }

    public void setNURSES_URI(String NURSES_URI) {
        this.NURSES_URI = NURSES_URI;
    }

    public String getPATIENTS_URI() {
        return PATIENTS_URI;
    }

    public void setPATIENTS_URI(String PATIENTS_URI) {
        this.PATIENTS_URI = PATIENTS_URI;
    }

    public String getCREDENTIALS() {
        return CREDENTIALS;
    }

    public void setCREDENTIALS(String CREDENTIALS) {
        this.CREDENTIALS = CREDENTIALS;
    }
}
