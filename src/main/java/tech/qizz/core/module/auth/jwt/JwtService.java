package tech.qizz.core.module.auth.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import tech.qizz.core.entity.User;
import tech.qizz.core.module.user.dto.ProfileResponse;
import tech.qizz.core.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private long EXPIRATION;


    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper mapper;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
        Map<String, Object> extraClaims,
        UserDetails userDetails
    ) {
        return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
            .signWith(getSigninKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigninKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    private Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public User extractUser(String token) {
        String userEmail = extractUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        if (!isTokenValid(token, userDetails)) {
            return null;
        }
        return userRepository.findByEmail(userEmail).orElse(null);
    }

    public void setJwtToCookie(HttpServletResponse response, String token) {
        setCookies(response, "token", token);
    }

    public void setUserDataToCookie(HttpServletResponse response, ProfileResponse user) {
        try {
            String userJson = URLEncoder.encode(mapper.writeValueAsString(user),
                StandardCharsets.UTF_8);
            setCookies(response, "user", userJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserCookies(HttpServletResponse response) {
        removeCookies(response, "token");
        removeCookies(response, "user");
    }

    public void setCookies(HttpServletResponse response, String key, String value) {
        setCookieForDomain(response, key, value, "qizz.tech");
        setCookieForDomain(response, key, value, "localhost");
    }

    public void removeCookies(HttpServletResponse response, String key) {
        removeCookieForDomain(response, key, "qizz.tech");
        removeCookieForDomain(response, key, "localhost");
    }

    private void setCookieForDomain(HttpServletResponse response, String key, String value,
        String domain) {
        Cookie cookie = new Cookie(key, value);
        cookie.setDomain(domain);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    private void removeCookieForDomain(HttpServletResponse response, String key, String domain) {
        Cookie cookie = new Cookie(key, "");
        cookie.setMaxAge(0);
        cookie.setDomain(domain);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
