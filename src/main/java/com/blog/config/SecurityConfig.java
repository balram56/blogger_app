package com.blog.config;

import com.blog.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfig extends WebSecurityConfigurerAdapter {//this config class

    public static final String[] PUBLIC_URLS= {"/api/auth/**",
            "/v3/api-docs",
            "/v2/api-docs",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/webjars/**"
    };

    @Autowired
    private CustomUserDetailsService userDetailsService;


     @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //disables CSRF (Cross-Site Request Forgery) protection.
                // CSRF is a security feature in web applications to prevent unauthorized actions by users.
                .csrf().disable()
                //indicates the beginning of configuring the authorization rules for requests.
                .authorizeRequests()
        //specifies that any HTTP GET request to URLs starting with "/api/" should be permitted without authentication.
               //which url who can acess or not
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                //url alow for sweger
                .antMatchers(PUBLIC_URLS).permitAll()

                //all the postmapping urls only admin can acess
                .antMatchers(HttpMethod.POST, "/api/auth/**").permitAll()

               //any request other than the ones defined previously should be authenticated.
                .anyRequest()
                .authenticated()
                //This is a chaining method that allows for further configuration.
                .and()
                ////This line indicates that HTTP Basic Authentication should be used. This means the client must include a username and password with each request.
                .httpBasic();
    }

    @Bean
    public PasswordEncoder getEncodedPassword(){
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
         return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
         auth.userDetailsService(userDetailsService)
                 .passwordEncoder(getEncodedPassword());
    }

//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//
//         //Object's consisting of username and password
//        UserDetails user = User.builder().username("balram").password(passwordEncoder.encode("testing")).roles("USER").build();
//        UserDetails admin = User.builder().username("admin").password(passwordEncoder.encode("Admin")).roles("ADMIN").build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }6
}
