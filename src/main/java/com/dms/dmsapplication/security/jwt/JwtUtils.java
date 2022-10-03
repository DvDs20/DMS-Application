package com.dms.dmsapplication.security.jwt;

import com.dms.dmsapplication.security.services.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    public static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${DMS.app.jwtSecret}")
    private String jwtSecret;

    @Value("${DMS.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        }
        catch (SignatureException signatureException) {
            logger.error("Invalid JWT signature: {}", signatureException.getMessage());
        }
        catch (MalformedJwtException malformedJwtException) {
            logger.error("Invalid JWT token: {}", malformedJwtException.getMessage());
        }
        catch (ExpiredJwtException expiredJwtException) {
            logger.error("JWT token is expired: {}", expiredJwtException.getMessage());
        }
        catch (UnsupportedJwtException unsupportedJwtException) {
            logger.error("JWT token is unsupported: {}", unsupportedJwtException.getMessage());
        }
        catch (IllegalArgumentException illegalArgumentException) {
            logger.error("JWT claims string is empty: {}", illegalArgumentException.getMessage());
        }
        return  false;
    }

}
