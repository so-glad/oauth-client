package so.glad.oauth.client;

/**
 * @author Cartoon Zhang
 * @since 2017/3/24 下午10:00
 */
public interface OAuthClient {

    AccessToken accessToken(TokenGrant tokenGrant);

}
