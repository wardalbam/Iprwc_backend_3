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
import org.springframework.web.servlet.config.annotation.CorsRegistry;

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

        // allow delete request all origins

        // http.cors().configurationSource(request -> new
        // CorsConfiguration().applyPermitDefaultValues());

        // allow delete request all origins
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
        http.headers().frameOptions().disable();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);

        // users
        http.authorizeRequests().antMatchers("/login/**", "api/token/refresh/**").permitAll();
        http.authorizeRequests().antMatchers(GET, "api/user/email").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN",
                "ROLE_MANAGER");
        http.authorizeRequests().antMatchers(GET, "api/user/details").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN",
                "ROLE_MANAGER");
        http.authorizeRequests().antMatchers(GET, "/api/user/all").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/user/all/roleuser").hasAnyAuthority("ROLE_ADMIN",
                "ROLE_MANAGER");
        http.authorizeRequests().antMatchers(GET, "/api/user/delete/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER");
        http.authorizeRequests().antMatchers(POST, "/api/user/save/manager").hasAnyAuthority("ROLE_ADMIN");

        // logout
        http.authorizeRequests().antMatchers(GET, "/api/logout").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN",
                "ROLE_MANAGER");

        // rooms
        http.authorizeRequests().antMatchers(GET, "/api/rooms").permitAll();
        http.authorizeRequests().antMatchers(GET, "/api/rooms/**").permitAll();
        // get /api/rooms/delete/** */
        http.authorizeRequests().antMatchers(DELETE, "/api/rooms/delete/**").hasAnyAuthority("ROLE_ADMIN",
                "ROLE_MANAGER");
        // post /api/rooms/create
        http.authorizeRequests().antMatchers(POST, "/api/rooms/create").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER");

        http.authorizeRequests().antMatchers(GET, "/api/room-types").permitAll();

        http.authorizeRequests().antMatchers(GET, "/api/avalible-time-slots").permitAll();

        // api/reservations/create
        http.authorizeRequests().antMatchers(POST, "/api/reservations/create").permitAll();
        // api/reservations/delete
        http.authorizeRequests().antMatchers(DELETE, "/api/reservations/delete/**").hasAnyAuthority("ROLE_ADMIN",
                "ROLE_MANAGER");

        http.addFilter(new CustomAuthentivationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.cors().and().csrf().disable();

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();
    }

}
