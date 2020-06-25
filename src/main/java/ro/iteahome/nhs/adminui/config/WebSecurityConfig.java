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
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private AdminService adminService;

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
                .antMatchers("...PUBLIC ENDPOINTS...").permitAll()
                .antMatchers("...ANONYMOUS ENDPOINTS...").anonymous()
                .antMatchers("...ADMIN-SPECIFIC-ENDPOINTS...").hasRole("ADMIN")
                .anyRequest().permitAll() // TODO: Change this to "anyRequest().authenticated"
                .and()

                .formLogin()
                .loginPage("/login")
                .and()

                .logout()
                .logoutUrl("/signout")
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
