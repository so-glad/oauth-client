package so.glad.oauth.client;

/**
 * @author Cartoon Zhang
 * @since 2017/3/25 下午7:49
 */
public interface TokenGrantProvider {

    TokenGrant defaultGrant(String... parameters);

    TokenGrant authorizationGrant(String code, String redirectUri);

    TokenGrant passwordGrant();

    TokenGrant clientGrant();

    TokenGrant refreshGrant(String refreshToken);

}
