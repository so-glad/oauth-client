package so.glad.oauth;

/**
 * @author Cartoon Zhang
 * @since 2017/3/15 下午1:10
 */
public interface Const {

    interface OAuth {
        String KEY_GRANT_TYPE = "grant_type";
        String KEY_CODE = "code";
        String KEY_REDIRECT_URI = "redirect_uri";
        String KEY_CLIENT_ID = "client_id";
        String KEY_CLIENT_SECRET = "client_secret";
        String KEY_USERNAME = "username";
        String KEY_PASSWORD = "password";
        String KEY_SCOPE = "scope";
        String KEY_REFRESH_TOKEN = "refresh_token";

        String GRANT_TYPE_AUTHORIZATION = "authorization_code";
        String GRANT_TYPE_CLIENT = "client_credentials";
        String GRANT_TYPE_PASSWORD = "password";
        String GRANT_TYPE_REFRESH = "refresh_token";
        String GRANT_TYPE_IMPLICIT = "implicit";
        String TOKEN_TYPE_BAERER = "Bearer";

        String HEADER_ACCEPT = "Accept";
        String HEADER_AUTHORIZATION = "Authorization";
        String PREFIX_BAERER = "Bearer ";

        String CACHE_OAUTH = "topup.cache.oauth";
        String CACHE_ACCESS_TOKEN = "access_token";
    }
}
