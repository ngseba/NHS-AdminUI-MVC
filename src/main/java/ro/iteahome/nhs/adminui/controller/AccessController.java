package ro.iteahome.nhs.adminui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AccessController {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------


// LINK "GET" REQUESTS: ------------------------------------------------------------------------------------------------

    @GetMapping("/home-initial")
    public String showHomePage() {
        return "home-initial";
    }

// METHODS: ------------------------------------------------------------------------------------------------------------


}
