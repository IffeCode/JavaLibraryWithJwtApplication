package org.example.javalibrarywithjwt.Config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Configuration
public class SecurityConfig {

    private static final String SECRET =
            "change-this-secret-key-before-production-123456789";

    @Bean
    //Hashar lösenordet
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Skapar en secretKey
    @Bean
    public SecretKey jwtSecretKey() {
        return new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8),
                "HmacSHA256");
    }

    //Verifierar Token
    @Bean
    public JwtDecoder jwtDecoder(SecretKey jwtSecretKey) {

        return NimbusJwtDecoder.withSecretKey(jwtSecretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

    //Skapar token
    @Bean
    public JwtEncoder jwtEncoder(SecretKey jwtSecretKey) {
        return new NimbusJwtEncoder(
                new ImmutableSecret<>(jwtSecretKey)
        );
    }

    //Regler för API:et - vem får åtkomst till API:et och hur användaren autentiseras
    @Bean
    public org.springframework.security.web.SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        // STATELESS innebär att servern inte sparar användarsessioner.
                        // Varje request måste innehålla ett giltigt JWT-token.
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Bestämmer vilka användare som får komma åt olika endpoints.
                .authorizeHttpRequests(auth -> auth

                        // Alla får registrera sig och logga in.
                        // Användaren behöver alltså inget JWT-token här.
                        .requestMatchers("/api/users/register", "/api/users/login")
                        .permitAll()


                        // USER och ADMIN får läsa böcker och författare.
                        // GET används för att hämta information.
                        // ** innebär att alla underliggande endpoints inkluderas.
                        .requestMatchers(HttpMethod.GET, "/api/books/**", "/api/authors/**")
                        .hasAnyRole("USER", "ADMIN")

                        // Endast ADMIN får skapa nya böcker.
                        // POST används för att skapa resurser.
                        .requestMatchers(HttpMethod.POST, "/api/books/**")
                        .hasRole("ADMIN")

                        // Endast ADMIN får uppdatera böcker.
                        // PUT används för att uppdatera befintliga resurser.
                        .requestMatchers(HttpMethod.PUT, "/api/books/**")
                        .hasRole("ADMIN")

                        // Endast ADMIN får radera böcker.
                        // DELETE används för att ta bort resurser.
                        .requestMatchers(HttpMethod.DELETE, "/api/books/**")
                        .hasRole("ADMIN")


                        // Alla andra endpoints kräver att användaren är inloggad. Exempelvis lånefunktioner.
                        .anyRequest()
                        .authenticated())

                /*
                Aktiverar JWT-autentisering. Spring Security läser token från Authorization-header,
                kontrollerar token med JwtDecoder och autentiserar användaren.
                 */
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        // Bygger och returnerar den färdiga säkerhetskonfigurationen.
        return http.build();
    }
}
