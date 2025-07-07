package sn.zeitune.oliveinsuranceinsured.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sn.zeitune.oliveinsuranceinsured.enums.UserRole;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(7);
        try {

            UserRole userType = jwtService.extractUserRoleWithoutValidation(token);

            log.info("Extracted UserType: {}", userType);

            String username = jwtService.extractUsername(token, userType);
            Claims claims = jwtService.extractAllClaims(token, userType);
            List<SimpleGrantedAuthority> authorities = jwtService.extractAuthorities(claims);
            UserDetails userDetails;
            if (userType == UserRole.ADMIN) {
                userDetails = Admin.builder()
                        .username(username)
                        .authorities(authorities)
                        .build();
            } else {
                String managementEntity = claims.get("managementEntity", String.class);
                userDetails = Employee.builder()
                        .username(username)
                        .authorities(authorities)
                        .managementEntity(UUID.fromString(managementEntity))
                        .build();
            }
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (JwtException | IllegalArgumentException ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"error\": \"Unauthorized\", \"message\": \"" + ex.getMessage() + "\"}"
            );
            return;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        filterChain.doFilter(request, response);
    }


}
