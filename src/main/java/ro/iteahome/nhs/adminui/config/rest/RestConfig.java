package ro.iteahome.nhs.adminui.config.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RestConfig {

// FIELDS: -------------------------------------------------------------------------------------------------------------

    @Value("${nhs.rest.server-url}")
    private String SERVER_URL;


}
