package ro.iteahome.nhs.adminui.config.rest;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "nhs.rest")
public class ConfigProperties {

// FIELDS: -------------------------------------------------------------------------------------------------------------

    private String serverUrl;

    private String rolesUri;

    private String adminsUri;

    private String clientAppsUri;

    private String institutionsUri;

    private String doctorsUri;

    private String nursesUri;

    private String patientsUri;

    private String credentials;

// GETTERS AND SETTERS: ------------------------------------------------------------------------------------------------

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getRolesUri() {
        return rolesUri;
    }

    public void setRolesUri(String rolesUri) {
        this.rolesUri = rolesUri;
    }

    public String getAdminsUri() {
        return adminsUri;
    }

    public void setAdminsUri(String adminsUri) {
        this.adminsUri = adminsUri;
    }

    public String getClientAppsUri() {
        return clientAppsUri;
    }

    public void setClientAppsUri(String clientAppsUri) {
        this.clientAppsUri = clientAppsUri;
    }

    public String getInstitutionsUri() {
        return institutionsUri;
    }

    public void setInstitutionsUri(String institutionsUri) {
        this.institutionsUri = institutionsUri;
    }

    public String getDoctorsUri() {
        return doctorsUri;
    }

    public void setDoctorsUri(String doctorsUri) {
        this.doctorsUri = doctorsUri;
    }

    public String getNursesUri() {
        return nursesUri;
    }

    public void setNursesUri(String nursesUri) {
        this.nursesUri = nursesUri;
    }

    public String getPatientsUri() {
        return patientsUri;
    }

    public void setPatientsUri(String patientsUri) {
        this.patientsUri = patientsUri;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }
}
