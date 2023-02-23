package com.example.Iprwc_backend.Security;

import com.example.Iprwc_backend.Filter.CustomAuthentivationFilter;
import com.example.Iprwc_backend.Filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);

        // users
        http.authorizeRequests().antMatchers("/login/**", "api/token/refresh/**").permitAll();
        http.authorizeRequests().antMatchers(GET, "api/user/email").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN", "ROLE_MANAGER");
        http.authorizeRequests().antMatchers(GET, "api/user/details").hasAnyAuthority("ROLE_USER",  "ROLE_ADMIN", "ROLE_MANAGER");
        http.authorizeRequests().antMatchers(GET, "/api/user/all").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/user/all/roleuser").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER");
        http.authorizeRequests().antMatchers(GET, "/api/user/delete/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER");
        http.authorizeRequests().antMatchers(POST, "/api/user/save/manager").hasAnyAuthority("ROLE_ADMIN");

        // Orders
        http.authorizeRequests().antMatchers(PUT, "/order/status/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER" );
        http.authorizeRequests().antMatchers(GET, "/order/all").hasAnyAuthority("ROLE_USER",  "ROLE_ADMIN", "ROLE_MANAGER");
        http.authorizeRequests().antMatchers(POST, "/order").hasAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(GET, "/order/{id}").hasAnyAuthority("ROLE_ADMIN", "ROELE_MANAGER");
        
        // product 
        http.authorizeRequests().antMatchers(GET, "/api/product/all").permitAll();
        http.authorizeRequests().antMatchers(GET, "/api/admin/product/all").hasAnyAuthority("ROLE_MANAGER","ROLE_ADMIN" );

        http.authorizeRequests().antMatchers(GET, "/api/product").permitAll();
        http.authorizeRequests().antMatchers(POST, "/api/product/add").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER");
        http.authorizeRequests().antMatchers(DELETE, "/api/product/delete/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER");

 
        http.addFilter(new CustomAuthentivationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

}
