package com.github.suloginscene.accountant.config;

import com.github.suloginscene.jwt.JwtReader;
import com.github.suloginscene.security.JwtSecurityFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import static com.github.suloginscene.security.Authorities.MEMBER;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtReader jwtReader;
    private final CorsConfigurationSource corsConfigurationSource;
    private final AccessDeniedHandler accessDeniedHandler;


    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .mvcMatchers(GET, "/api")
                .mvcMatchers(GET, "/docs/index.html");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS);

        http
                .authorizeRequests().anyRequest().hasAuthority(MEMBER);

        http
                .addFilterBefore(new JwtSecurityFilter(jwtReader), UsernamePasswordAuthenticationFilter.class);

        http
                .cors().configurationSource(corsConfigurationSource);

        http
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }

}
