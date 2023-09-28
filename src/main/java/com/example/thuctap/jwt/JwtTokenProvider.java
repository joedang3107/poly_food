package com.example.thuctap.jwt;

import com.example.thuctap.security.CustomUserDetails;
import io.jsonwebtoken.*;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${thuctap.jwt.secret}")
    private String JWT_SECRET;
    @Value("${thuctap.jwt.expiration}")
    private int JWT_EXPIRATION;

    // tao token tu thong tin user
    public String generateToken (CustomUserDetails customUserDetails) {
        // tao chuoi jwt tu user

        Date now = new Date();

        return Jwts.builder()
                .setSubject(customUserDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + JWT_EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();

    }

    public String getUserNameFromJwt(String token) {

        Claims claims = Jwts.parser().setSigningKey(JWT_SECRET)
                .parseClaimsJws(token).getBody();
        return claims.getSubject();

    }

//    private Key key() {
//        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));
//    }
//
//    public String getUserNameFromJwt(String token) {
//        return Jwts.parserBuilder().setSigningKey(key()).build()
//                .parseClaimsJws(token).getBody().getSubject();
//    }

    public boolean validateToken(String token){
        try {
//            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        }
        catch (MalformedJwtException exception){
            log.error("Invalid Jwt exception");
        }
        catch (ExpiredJwtException exception){
            log.error("expired Jwt token");
        }
        catch (UnsupportedJwtException exception){
            log.error("Unsupported Jwt exception");
        }
        catch (IllegalArgumentException exception){
            log.error("Jwt claims string empty");
        }
        return false;
    }
}
