package so.glad.oauth.client;

import lombok.Setter;
import lombok.experimental.Accessors;
import so.glad.environment.Configuration;
import so.glad.oauth.Config;
import so.glad.oauth.Const;

import javax.annotation.Resource;

/**
 * @author Cartoon Zhang
 * @since 2017/3/25 下午8:11
 */
@Accessors(chain = true)
public class TokenGrantProviderImpl implements TokenGrantProvider {

    @Setter
    @Resource
    private Configuration config;

    @Override
    public TokenGrant defaultGrant(String...parameters) {
        String grantType = config.getConfig(Config.Key.GRANT_TYPE, Const.OAuth.GRANT_TYPE_AUTHORIZATION);
        switch (grantType) {
            case Const.OAuth.GRANT_TYPE_CLIENT:
                return clientGrant();
            case Const.OAuth.GRANT_TYPE_PASSWORD:
                return passwordGrant();
            case Const.OAuth.GRANT_TYPE_REFRESH:
                return refreshGrant(parameters[0]);
            default:
            case Const.OAuth.GRANT_TYPE_AUTHORIZATION:
                return authorizationGrant(parameters[0], parameters[1]);
        }
    }

    @Override
    public TokenGrant authorizationGrant(String code, String redirectUri) {
        return new TokenGrant().setGrantType(Const.OAuth.GRANT_TYPE_AUTHORIZATION)
                .setClientId(config.getConfig(Config.Key.CLIENT_ID))
                .setCode(code)
                .setRedirectUri(redirectUri);
    }

    @Override
    public TokenGrant refreshGrant(String refreshToken) {
        return new TokenGrant().setGrantType(Const.OAuth.GRANT_TYPE_REFRESH)
                .setClientId(config.getConfig(Config.Key.CLIENT_ID))
                .setClientSecret(config.getConfig(Config.Key.CLIENT_SECRET))
                .setRefreshToken(refreshToken);
    }

    @Override
    public TokenGrant passwordGrant() {
        return new TokenGrant().setGrantType(Const.OAuth.GRANT_TYPE_PASSWORD)
                .setClientId(config.getConfig(Config.Key.CLIENT_ID))
                .setClientSecret(config.getConfig(Config.Key.CLIENT_SECRET))
                .setUsername(config.getConfig(Config.Key.USERNAME))
                .setPassword(config.getConfig(Config.Key.PASSWORD));
    }

    @Override
    public TokenGrant clientGrant() {
        return new TokenGrant().setGrantType(Const.OAuth.GRANT_TYPE_CLIENT)
                .setClientId(config.getConfig(Config.Key.CLIENT_ID))
                .setClientSecret(config.getConfig(Config.Key.CLIENT_SECRET));
    }

}
