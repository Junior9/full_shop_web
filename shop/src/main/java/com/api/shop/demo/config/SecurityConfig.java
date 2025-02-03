package com.api.shop.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.api.shop.demo.service.user.Userservice;

import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .csrf(csrf -> csrf.disable())
            .httpBasic(Customizer.withDefaults())
            .sessionManagement(session -> {
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);})
            .authorizeHttpRequests(request ->{
                request.requestMatchers(HttpMethod.GET,"/api/product/all").permitAll();
                request.requestMatchers(HttpMethod.GET,"/api/product/{id}").permitAll();
                request.requestMatchers(HttpMethod.GET,"/api/category/all").permitAll();
                request.requestMatchers(HttpMethod.GET,"/api/category/id/{id}").permitAll();
                request.requestMatchers(HttpMethod.GET,"/api/category/name/{name}").permitAll();
                request.requestMatchers(HttpMethod.GET,"/api/category/all").permitAll();
                request.requestMatchers(HttpMethod.POST,"/api/user/").permitAll();

                request.requestMatchers(HttpMethod.GET,"/api/user/all").hasAuthority("READ_ADM");
                request.requestMatchers(HttpMethod.POST,"/api/product/").hasAuthority("CREATE_ADM");
                request.requestMatchers(HttpMethod.POST,"/api/category/").hasAuthority("CREATE_ADM");
                request.requestMatchers(HttpMethod.PUT,"/api/category/{id}").hasAuthority("CREATE_ADM");
                request.requestMatchers(HttpMethod.DELETE,"/api/category/{id}").hasAuthority("CREATE_ADM");
                //request.requestMatchers(HttpMethod.PUT,"/api/product/{id}").hasAuthority("CREATE_ADM");

                request.anyRequest().authenticated();
            })
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        return this.authenticationConfiguration.getAuthenticationManager();
    }

    @Bean 
    public AuthenticationProvider authenticationProvider(Userservice userService){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    //Create users in memory to test local
    /* 
    @Bean
    public UserDetailsService userDetailsService(){
        List<UserDetails> usersDetails = new ArrayList<>();

        UserDetails userDetails = User.withUsername("userAdm")
            .password("12345")
            .roles("ADM")
            .authorities("UPDATE_ADM","CREATE_ADM","READ_ADM")
            .build();
        
        UserDetails userDetails2 = User.withUsername("user")
            .password("12345")
            .roles("CUSTOMER")
            .authorities("READ","CREATE")
            .build();

        usersDetails.add(userDetails);
        usersDetails.add(userDetails2);

        return new InMemoryUserDetailsManager(usersDetails);
    }
    */
    

}
