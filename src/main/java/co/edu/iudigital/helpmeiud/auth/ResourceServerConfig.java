package co.edu.iudigital.helpmeiud.auth;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	// protección del lado de oath2
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/users/signup**").permitAll()
			.antMatchers(HttpMethod.OPTIONS, "/users/signup**").permitAll()
			.antMatchers(HttpMethod.GET, "/cases", "/cases/**").permitAll()
			// nivel 2: AUTORIZACIONES ESPECÍFICAS (SOBREESCRIBE LA GENÉRICA CORRESPONDIENTE)
			.antMatchers(HttpMethod.GET, "/users/profile/img**").permitAll()
			// nivel 1: genéricas
			.anyRequest().authenticated()
			.and()
			.cors().configurationSource(corsConfigurationSource())
			.and().csrf().disable();

	}

	//---------------------------( 2 )---------------------------

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS"));
		config.setAllowCredentials(true);//permitir credenciales
		config.setAllowedOriginPatterns(Arrays.asList("*"));
		config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
}
