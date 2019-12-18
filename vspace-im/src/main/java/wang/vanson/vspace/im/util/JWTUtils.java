package wang.vanson.vspace.im.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {
    private static final String SECRET = "v-space_secret_2019";
    private static final String ISSURE = "v-space";

    private static Algorithm algorithm() {
        return Algorithm.HMAC256(SECRET);
    }

    public static JWTCreator.Builder builder() {
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("alg", "HS256");
        headerMap.put("typ", "JWT");

        return JWT.create()
                .withHeader(headerMap)
                .withIssuer(ISSURE)
                .withAudience("Web")
                .withIssuedAt(new Date());
    }

    public static String createToken(String subject, String userId) {
        return builder().withSubject(subject)
                .withClaim("uid", userId)
                .sign(algorithm());
    }

    public static String createToken(String userId) {
        return createToken("access", userId);
    }

    public static String verifyToken(String token) {
        String userId = "";
        JWTVerifier verifier = JWT.require(algorithm())
                .withIssuer(ISSURE)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        Map<String, Claim> claims = jwt.getClaims();
        Claim claim = claims.get("uid");
        if (claim != null) {
            userId = claim.asString();
        }
        return userId;
    }
}
