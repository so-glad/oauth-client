package so.glad.oauth.client;

import org.junit.Assert;
import org.junit.Test;
import so.glad.oauth.Const;

/**
 * @author Cartoon Zhang
 * @since 2017/3/26 下午6:07
 */

public class TokenGrantProviderImplTest {

    static TokenGrantProvider tokenGrantProvider = new TokenGrantProviderImpl().setConfig(new PropertiesConfiguration());

    @Test
    public void testAuthorizationGrant() {
        TokenGrant tokenGrant = tokenGrantProvider.authorizationGrant("code12345", "http://redirect.to");
        Assert.assertEquals(Const.OAuth.GRANT_TYPE_AUTHORIZATION, tokenGrant.getGrantType());
        Assert.assertNull(tokenGrant.getUsername());
        Assert.assertNull(tokenGrant.getPassword());
        Assert.assertEquals("client_id_123456", tokenGrant.getClientId());
        Assert.assertNull(tokenGrant.getClientSecret());
        Assert.assertNull(tokenGrant.getRefreshToken());
        Assert.assertEquals("code12345", tokenGrant.getCode());
        Assert.assertEquals("http://redirect.to", tokenGrant.getRedirectUri());
        Assert.assertEquals("*", tokenGrant.getScope());
        Assert.assertEquals("{\"grant_type\": \"authorization_code\",\"code\": \"code12345\",\"redirect_uri\": \"http://redirect.to\",\"client_id\": \"client_id_123456\",\"scope\": \"*\"}", tokenGrant.toJson());
    }

    @Test
    public void testPasswordGrant() {
        TokenGrant tokenGrant = tokenGrantProvider.passwordGrant();
        Assert.assertEquals(Const.OAuth.GRANT_TYPE_PASSWORD, tokenGrant.getGrantType());
        Assert.assertEquals("soglad_username", tokenGrant.getUsername());
        Assert.assertEquals("soglad_password", tokenGrant.getPassword());
        Assert.assertEquals("client_id_123456", tokenGrant.getClientId());
        Assert.assertEquals("client_secrete_123456", tokenGrant.getClientSecret());
        Assert.assertNull(tokenGrant.getRefreshToken());
        Assert.assertNull(tokenGrant.getCode());
        Assert.assertNull(tokenGrant.getRedirectUri());
        Assert.assertEquals("*", tokenGrant.getScope());
        Assert.assertEquals("{\"grant_type\": \"password\",\"client_id\": \"client_id_123456\",\"client_secret\": \"client_secrete_123456\",\"username\": \"soglad_username\",\"password\": \"soglad_password\",\"scope\": \"*\"}", tokenGrant.toJson());
    }

    @Test
    public void testClientGrant() {
        TokenGrant tokenGrant = tokenGrantProvider.clientGrant();
        Assert.assertEquals(Const.OAuth.GRANT_TYPE_CLIENT, tokenGrant.getGrantType());
        Assert.assertNull( tokenGrant.getUsername());
        Assert.assertNull( tokenGrant.getPassword());
        Assert.assertEquals("client_id_123456", tokenGrant.getClientId());
        Assert.assertEquals("client_secrete_123456", tokenGrant.getClientSecret());
        Assert.assertNull(tokenGrant.getRefreshToken());
        Assert.assertNull(tokenGrant.getCode());
        Assert.assertNull(tokenGrant.getRedirectUri());
        Assert.assertEquals("*", tokenGrant.getScope());
        Assert.assertEquals("{\"grant_type\": \"client_credentials\",\"client_id\": \"client_id_123456\",\"client_secret\": \"client_secrete_123456\",\"scope\": \"*\"}", tokenGrant.toJson());
    }

    @Test
    public void testRefreshGrant(){
        TokenGrant tokenGrant = tokenGrantProvider.refreshGrant("refresh_123456");
        Assert.assertEquals(Const.OAuth.GRANT_TYPE_REFRESH, tokenGrant.getGrantType());
        Assert.assertNull( tokenGrant.getUsername());
        Assert.assertNull( tokenGrant.getPassword());
        Assert.assertEquals("client_id_123456", tokenGrant.getClientId());
        Assert.assertEquals("client_secrete_123456", tokenGrant.getClientSecret());
        Assert.assertEquals("refresh_123456", tokenGrant.getRefreshToken());
        Assert.assertNull(tokenGrant.getCode());
        Assert.assertNull(tokenGrant.getRedirectUri());
        Assert.assertEquals("*", tokenGrant.getScope());
        Assert.assertEquals("{\"grant_type\": \"refresh_token\",\"client_id\": \"client_id_123456\",\"client_secret\": \"client_secrete_123456\",\"refresh_token\": \"refresh_123456\",\"scope\": \"*\"}", tokenGrant.toJson());
    }
}
