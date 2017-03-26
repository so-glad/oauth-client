package so.glad.oauth;

/**
 * @author Cartoon Zhang
 * @since 2017/3/26 下午5:36
 */
public interface Config {
    interface Key {
        String TEST_ENV = "oauth.env.test";
        String GRANT_TYPE = "oauth.grant.type";
        String USERNAME = "oauth.server.username";
        String PASSWORD = "oauth.server.password";
        String CLIENT_ID = "oauth.client.id";
        String CLIENT_SECRET = "oauth.client.secret";
    }
}
