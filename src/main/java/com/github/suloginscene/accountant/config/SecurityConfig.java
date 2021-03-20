package com.github.suloginscene.accountant.config;

import com.github.suloginscene.jwtconfig.JwtSecurityFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtSecurityFilter jwtSecurityFilter;
    private final CorsConfigurationSource corsConfigurationSource;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS);

        http
                .authorizeRequests().anyRequest().authenticated();

        http
                .addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .cors().configurationSource(corsConfigurationSource);
    }

}
