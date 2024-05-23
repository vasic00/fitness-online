package unibl.etf.ip.fitnessonline.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import unibl.etf.ip.fitnessonline.services.JwtUserDetailsService;

@EnableWebSecurity
@Configuration
@EnableAsync
public class WebSecurityConfiguration {

    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    public WebSecurityConfiguration(JwtUserDetailsService jwtUserDetailsService, JwtAuthorizationFilter jwtAuthorizationFilter, JwtAuthenticationEntryPoint authenticationEntryPoint) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {      //service that provides password encryption and encoding
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilter(corsFilter())
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(eh -> eh.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(conf -> {
                    conf.requestMatchers(HttpMethod.POST, "/programs/**").authenticated()
                            .requestMatchers(HttpMethod.DELETE, "/programs/**").authenticated()
                            .requestMatchers(HttpMethod.GET, "/programs/creator").authenticated()
                            .requestMatchers(HttpMethod.GET, "/programs/creator/**").authenticated()
                            .requestMatchers(HttpMethod.POST, "/purchases").authenticated()
                            .requestMatchers(HttpMethod.GET, "/purchases/**").authenticated()
                            .requestMatchers(HttpMethod.POST, "/messages").authenticated()
                            .requestMatchers(HttpMethod.GET, "/messages/received").authenticated()
                            .requestMatchers(HttpMethod.PUT, "/messages/received/*").authenticated()
                            .requestMatchers(HttpMethod.DELETE, "/messages/received/*").authenticated()
                            .requestMatchers(HttpMethod.GET, "/messages/received/filtered").authenticated()
                            .requestMatchers(HttpMethod.POST, "/comments").authenticated()
                            .requestMatchers(HttpMethod.POST, "/images").authenticated()
                            .requestMatchers(HttpMethod.GET, "/exercises").authenticated()
                            .requestMatchers(HttpMethod.POST, "/exercises").authenticated()
                            .requestMatchers(HttpMethod.GET, "/**").permitAll() //enables unauthorized requests
                            .requestMatchers(HttpMethod.POST, "/**").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/**").permitAll()
                            .requestMatchers(HttpMethod.DELETE, "/**").permitAll();
                    conf.anyRequest().authenticated();
                });
        return http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class).build();
    }


//    private HttpSecurity createAuthorizationRules(HttpSecurity http) throws Exception {
//        AuthorizationRules authorizationRules = new ObjectMapper().readValue(new ClassPathResource("rules.json").getInputStream(), AuthorizationRules.class);
//        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry interceptor = http.authorizeRequests();
//        interceptor = interceptor.antMatchers(HttpMethod.POST, "/login").permitAll().antMatchers(HttpMethod.POST, "/sign-up").permitAll();
//        for (Rule rule : authorizationRules.getRules()) {
//            if (rule.getMethods().isEmpty())
//                interceptor = interceptor.antMatchers(rule.getPattern()).hasAnyAuthority(rule.getRoles().toArray(String[]::new));
//            else for (String method : rule.getMethods()) {
//                interceptor = interceptor.antMatchers(HttpMethod.resolve(method), rule.getPattern()).hasAnyAuthority(rule.getRoles().toArray(String[]::new));
//            }
//        }
//        return interceptor.anyRequest().denyAll().and();
//    }

     //cors filter - we shouldn't allow all methods origins and headers...
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    //changes role prefix that is added automatically
    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }

}
