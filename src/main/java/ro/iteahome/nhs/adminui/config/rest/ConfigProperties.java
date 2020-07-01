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


// GETTERS AND SETTERS: ------------------------------------------------------------------------------------------------



}
