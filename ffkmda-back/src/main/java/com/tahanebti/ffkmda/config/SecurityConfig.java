package com.tahanebti.ffkmda.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tahanebti.ffkmda.security.JwtAuthenticationFilter;



@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

	 	//private final JwtAuthenticationFilter tokenAuthenticationFilter;

	 	

		@Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	        return authenticationConfiguration.getAuthenticationManager();
	    }
		
		
	        
	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    	// @formatter:off
	        // Set Content Security Policy to allow only particular script source(s) and resource(s)
	        // style-src 'unsafe-inline' for Angular to work properly
	    	http.headers().contentSecurityPolicy("default-src 'self'; " +
	                   "style-src 'self' 'unsafe-inline' https://fonts.googleapis.com; " +
	                   "font-src 'self' https://fonts.gstatic.com");
	    	
	    	
	    	
	        http.authorizeRequests()
	            //    .antMatchers(HttpMethod.POST, "/api/orders").hasAnyAuthority(ADMIN, USER)
	            //    .antMatchers(HttpMethod.GET, "/api/users/me").hasAnyAuthority(ADMIN, USER)
	            //    .antMatchers("/api/orders", "/api/orders/**").hasAuthority(ADMIN)
	            //    .antMatchers("/api/users", "/api/users/**").hasAuthority(ADMIN)
	                .antMatchers("/public/**", "/auth/**").permitAll()
	                .antMatchers("/", "/error", "/csrf", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**").permitAll()
	                .antMatchers("/api/test/**").permitAll() // permit the class of test
	                .antMatchers("/api/v1/structures/**").permitAll() 
	                .antMatchers("/api/v1/clubs/**").permitAll() 
	                .antMatchers("https://extranet.ffkmda.fr/**").permitAll()
	                .antMatchers("/**").permitAll()  // permit all the routers after swagger-ui.html
	                .anyRequest().authenticated();
	      //  http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	        http.exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
	        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	        http.cors().and().csrf().disable();
	        return http.build();
	    }


		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}


	    public static final String ADMIN = "ADMIN";
	    public static final String USER = "USER";
}
