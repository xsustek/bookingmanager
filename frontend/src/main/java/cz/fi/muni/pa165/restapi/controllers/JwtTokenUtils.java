package cz.fi.muni.pa165.restapi.controllers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Tomas Kopecky
 */
@Component
public class JwtTokenUtils implements Serializable {

    final static Logger logger = LoggerFactory.getLogger(JwsHeader.class);

    /**
     * Check from token, if token can be refreshed
     *
     * @param token token to be used
     * @return True if token can be refreshed False otherwise
     */
    public Boolean isTokenValid(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e ){
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
}