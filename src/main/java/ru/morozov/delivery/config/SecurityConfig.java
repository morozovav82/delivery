package ru.morozov.delivery.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import ru.morozov.delivery.security.AuthProvider;
import ru.morozov.delivery.security.HeadersAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthProvider authProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(headersAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/public/**").permitAll()
                .antMatchers("/tests/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/swagger*/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();

        sessionManagement(http);
        cacheManagement(http);
    }

    protected final HttpSecurity sessionManagement(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .sessionManagement()
                //Не создавать сессию
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();
    }

    protected final HttpSecurity cacheManagement(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                //Не создавать сессию при неудачной аутентификации
                .requestCache().requestCache(new NullRequestCache())
                .and();
    }

    private Filter headersAuthenticationFilter() throws Exception {
        final HeadersAuthenticationFilter filter = new HeadersAuthenticationFilter(
                new AndRequestMatcher(
                    new NegatedRequestMatcher(new AntPathRequestMatcher("/public/**")),
                        new NegatedRequestMatcher(new AntPathRequestMatcher("/tests/**")),
                        new NegatedRequestMatcher(new AntPathRequestMatcher("/actuator/**")),
                        new NegatedRequestMatcher(new AntPathRequestMatcher("/swagger*/**")),
                        new NegatedRequestMatcher(new AntPathRequestMatcher("/v2/api-docs")),
                        new NegatedRequestMatcher(new AntPathRequestMatcher("/webjars/**"))
                ));
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }
}
