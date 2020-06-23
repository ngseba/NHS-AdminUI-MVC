package ro.iteahome.nhs.adminui.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import ro.iteahome.nhs.adminui.service.AdminService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

// AUTHENTICATION MANAGEMENT: ------------------------------------------------------------------------------------------

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(adminService)
                .passwordEncoder(passwordEncoder);
    }

// AUTHORIZATION MANAGEMENT: -------------------------------------------------------------------------------------------

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("...PUBLIC ENDPOINTS...").permitAll() // TODO: Add actual endpoints to this list.
                .antMatchers("...ANONYMOUS ENDPOINTS...").anonymous() // TODO: Add actual endpoints to this list.
                .antMatchers("...ADMIN-ROLE-ENDPOINTS...").hasRole("ADMIN") // TODO: Add actual endpoints to this list.
                .anyRequest().authenticated() // FAIL-SAFE: ALL OTHER REQUEST MUST BE AUTHENTICATED
                .and()

                .formLogin()
                .loginPage("/login") // TODO: Develop a login page
                // TODO: Configure access to login: only unauthenticated users.
                .and()

                .logout()
                .logoutUrl("/signout")
                // TODO: Configure logout parameters: only authenticated users.
                .and()

                .csrf().disable() // TODO: Configure this, instead of avoiding it.
                .headers().frameOptions().disable(); // TODO: Configure this, instead of avoiding it.
    }

// WEB SECURITY MANAGEMENT (FOR TEMPLATES): ----------------------------------------------------------------------------

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers("/css/**", "/img/**", "/js/**");
    }
}
