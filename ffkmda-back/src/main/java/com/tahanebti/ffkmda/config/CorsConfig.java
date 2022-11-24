package com.tahanebti.ffkmda.config;


import java.util.Collections;
import java.util.List;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CorsConfig  {

	 
	     @Bean
	    public CorsFilter corsFilter(@Value("${app.cors.allowed-origins}") List<String> allowedOrigins) {
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        CorsConfiguration config = new CorsConfiguration();
	        config.setAllowCredentials(true);
	        config.setAllowedOriginPatterns(Collections.singletonList("*"));    
	        config.addAllowedMethod("*");
	        config.addAllowedHeader("*");
	        source.registerCorsConfiguration("/**", config);
	        return new CorsFilter(source);
	    }
}
