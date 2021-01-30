package hranj.marijan.springbootapp.constants;

public final class JwtConstants {

    private JwtConstants() {}

    public static final String SECRET = "SECRET_KEY";
    public static final long EXPIRATION_TIME = 900_000;
    public static final String JWT_TOKEN_HEADER_KEY = "jwt-token";
    public static final String SIGN_IN_URL = "/v1/user/signin";

}
