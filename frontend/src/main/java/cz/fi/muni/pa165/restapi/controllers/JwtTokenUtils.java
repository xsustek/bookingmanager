package cz.fi.muni.pa165.restapi.controllers;

import cz.fi.muni.pa165.dto.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tomas Kopecky
 */
@Component
public class JwtTokenUtils implements Serializable {

    final static Logger logger = LoggerFactory.getLogger(JwsHeader.class);

    private static final String CLAIM_KEY_ID = "id";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String CLAIM_KEY_ROLE = "role";

    /**
     * Check from token, if token can be refreshed
     *
     * @param token token to be used
     * @return True if token can be refreshed False otherwise
     */
    public Boolean isTokenValid(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            throw new IllegalArgumentException("Token is invalid");
        }
    }

    /**
     * Method for dissection claims from the token
     *
     * @param token token to be used
     * @return Claims object, a JSON map and any values can be added to it
     */
    private Claims getClaimsFromToken(String token) {

        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey("secret".getBytes("UTF-8"))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * Method for dissection expiration date from the token
     *
     * @param token token which will be used
     * @return expiration date of the token
     */
    private Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    /**
     * Check if token is expired
     *
     * @param token token to be used
     * @return True if not expired False otherwise
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Token generator which uses userDetails and device for generating
     *
     * @return generated token
     */
    public String generateToken(UserDTO dto) throws UnsupportedEncodingException {

        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_ID, dto.getId());
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put(CLAIM_KEY_ROLE, dto.getRole());
        return generateToken(claims);
    }

    /**
     * Private method for generating token using SignatureAlgorithm.HS512
     *
     * @param claims claims object for token generating
     * @return generated token
     */
    private String generateToken(Map<String, Object> claims) throws UnsupportedEncodingException {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256, "secret".getBytes("UTF-8"))
                .compact();
    }

    /**
     * Generator for expiration time
     *
     * @return expiration time
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + 36000 * 1000);
    }

    /**
     * Is user ADMIN
     *
     * @return expiration time
     */
    public Boolean checkRole(String token) {
        Claims claims = getClaimsFromToken(token);

        logger.error(claims.toString());

        return claims.get("role").equals("ADMIN");
    }
}