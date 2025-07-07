package sn.zeitune.oliveinsuranceinsured.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import sn.zeitune.oliveinsuranceinsured.enums.UserRole;

import java.security.PublicKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final KeyProviderService keyProvider;

    @Value("${jwt.user.expiration}")
    private long userExp;

    @Value("${jwt.admin.expiration}")
    private long adminExp;

    private long getExpiration(UserRole userType) {
        if (userType == UserRole.ADMIN) {
            return adminExp;
        } else if (userType == UserRole.USER) {
            return userExp;
        }
        throw new IllegalArgumentException("Invalid user type: " + userType);
    }

    public String extractUsername(String token, UserRole userType) throws Exception {
        return extractClaim(token, Claims::getSubject, userType);
    }

    public List<SimpleGrantedAuthority> extractAuthorities(Claims claims) {
        List<String> roles = claims.get("authorities", List.class);
        if (roles == null) return List.of();
        return roles.stream().map(SimpleGrantedAuthority::new).toList();
    }



    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver, UserRole userType) throws Exception {
        final Claims claims = extractAllClaims(token, userType);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token, UserRole userType) throws Exception {
        PublicKey publicKey = keyProvider.getPublicKey(userType);
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public UserRole extractUserRoleWithoutValidation(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) throw new IllegalArgumentException("Invalid JWT");

        String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
        JSONObject payload = new JSONObject(payloadJson);
        return UserRole.valueOf(payload.getString("userType"));
    }

    public boolean isTokenValid(String token, UserRole role) throws Exception {
        final String username = extractUsername(token, role);
        return username != null && !isTokenExpired(token, role);
    }

    private boolean isTokenExpired(String token, UserRole userType) throws Exception {
        Date expiration = extractClaim(token, Claims::getExpiration, userType);
        return expiration.before(new Date());
    }
}
