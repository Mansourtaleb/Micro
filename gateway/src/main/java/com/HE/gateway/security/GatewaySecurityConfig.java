package com.HE.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.core.convert.converter.Converter;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableWebFluxSecurity
@EnableMethodSecurity
public class GatewaySecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(ex -> ex
                        .pathMatchers("/actuator/**").permitAll()
                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .pathMatchers("/users/public/**").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth -> oauth
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter()))
                )
                .build();
    }

    /** WebFlux expects a reactive converter: Jwt -> Mono<AbstractAuthenticationToken> */
    private Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthConverter() {
        return jwt -> {
            Collection<GrantedAuthority> authorities = extractAuthorities(jwt);
            return Mono.just(new JwtAuthenticationToken(jwt, authorities));
        };
    }

    /** Collect ROLE_* from realm_access.roles and resource_access.users.roles */
    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        Set<String> roles = new HashSet<>();

        Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
        if (realmAccess != null) {
            Object r = realmAccess.get("roles");
            if (r instanceof Collection<?> col) {
                roles.addAll(col.stream().filter(Objects::nonNull).map(Object::toString).toList());
            }
        }

        Map<String, Object> resourceAccess = jwt.getClaimAsMap("resource_access");
        if (resourceAccess != null) {
            Object usersClient = resourceAccess.get("users"); // adapte le nom du client si différent
            if (usersClient instanceof Map<?, ?> usersMap) {
                Object r = usersMap.get("roles");
                if (r instanceof Collection<?> col) {
                    roles.addAll(col.stream().filter(Objects::nonNull).map(Object::toString).toList());
                }
            }
        }

        return roles.stream()
                .map(s -> "ROLE_" + s.toUpperCase())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
