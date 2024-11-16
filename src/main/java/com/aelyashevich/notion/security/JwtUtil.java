package com.aelyashevich.notion.security;

import com.aelyashevich.notion.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.experimental.UtilityClass;

import java.util.Date;
import java.util.Map;

@UtilityClass
public class JwtUtil {

    private final String secret = "984hg493gh0439rthr0429uruj2309yh937gc763fe87t3f89723gf";

    private final Long TOKEN_LIFETIME = 86_400_000L;

    public static String extractId(final String token){
        return getClaimsFromToken(token).getSubject();
    }

    public static String generateToken(final User user) {
        return createToken(user);
    }

    private static Claims getClaimsFromToken(final String token) {
        return Jwts
                .parserBuilder()
                .setSigningKeyResolver(new SigningKeyResolverAdapter() {
                    @Override
                    public byte[] resolveSigningKeyBytes(JwsHeader header, Claims claims) {
                        return secret.getBytes();
                    }
                })
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private static String createToken(final User user) {
        var issuedAt = new Date();
        var expirationDate = new Date(issuedAt.getTime() + TOKEN_LIFETIME);

        return Jwts.builder()
                .setSubject(user.getId())
                .setIssuedAt(issuedAt)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public static boolean validate(final String token) {
        return !getClaimsFromToken(token).getSubject().isEmpty()
                || getClaimsFromToken(token).getExpiration().before(new Date());
    }
}
