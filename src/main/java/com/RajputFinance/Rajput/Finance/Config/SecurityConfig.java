package com.RajputFinance.Rajput.Finance.Config;

//@Configuration
public class SecurityConfig {

//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    // Inject the JwtAuthenticationFilter into the configuration
//    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
//        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .requestMatchers("/api/auth/**").permitAll() // Allow authentication-related endpoints
//                .anyRequest().authenticated() // Secure other endpoints
//                .and()
//                .addFilter(new JwtAuthenticationFilter()) // Add the JWT filter
//                .httpBasic(); // Optional: configure basic authentication if needed
//
//        return http.build();
//    }
}
