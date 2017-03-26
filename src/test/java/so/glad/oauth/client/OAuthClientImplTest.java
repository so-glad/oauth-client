package so.glad.oauth.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import mockit.*;
import mockit.integration.junit4.JMockit;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import so.glad.oauth.Const;

import javax.cache.CacheManager;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Cartoon Zhang
 * @since 2017/3/26 下午7:13
 */
@RunWith(JMockit.class)
public class OAuthClientImplTest {

    @Tested
    static OAuthClientImpl oAuthClient = new OAuthClientImpl() {};
    @Injectable
    HttpClient httpClient;
    @Injectable
    ObjectMapper objectMapper;
    @Injectable
    Configuration configuration;
    @Injectable
    TokenGrantProvider tokenGrantProvider;
    @Injectable
    CacheManager cacheManager;

    @Test
    public void testAccessToken( @Mocked HttpResponse httpResponse,
                                 @Mocked HttpEntity httpEntity) throws IOException {
//        HttpUriRequest post = post();
        AccessToken expectToken = expectedAccessToken();
        new Expectations() {{
                httpClient.execute((HttpUriRequest)any); result = httpResponse;
                httpResponse.getEntity(); result = httpEntity;
                httpEntity.getContent(); result = prepareInputStream();
                objectMapper.readValue((InputStream) any, AccessToken.class); result = expectToken;
            }};

        Assert.assertEquals(expectToken, oAuthClient.accessToken(prepareTokenGrant()));

        new Verifications(){{
                httpClient.execute((HttpUriRequest)any); times = 1;
                httpResponse.getEntity(); times = 1;
                httpEntity.getContent(); times = 1;
                objectMapper.readValue((InputStream)any, AccessToken.class); times = 1;
        }};
    }

//    private HttpUriRequest post() {
//        HttpPost post = new HttpPost("https://topup.glad.so/oauth/token");
//        post.setHeaders(new Header[]{new BasicHeader("Accept", "application/json")});
//        String grantString = "{\"grant_type\": \"password\",\"client_id\": \"client_id\",\"client_secret\": \"client_password\",\"username\": \"username\",\"password\": \"password\",\"scope\": \"*\"}";
//        post.setEntity(new StringEntity(grantString, ContentType.APPLICATION_JSON));
//        return post;
//    }

    private ByteArrayInputStream prepareInputStream() {
        String content = "{\"access_token\": \"accessToken\", \"expires_in\": 72000, \"refresh_token\": \"refreshToken\",\"token_type\": \"Bearer\"}";
        return new ByteArrayInputStream(content.getBytes());
    }

    private AccessToken expectedAccessToken() {
        AccessToken accessToken = new AccessToken();
        accessToken.setAccessToken("accessToken");
        accessToken.setRefreshToken("refreshToken");
        accessToken.setExpiresIn(72000);
        return accessToken;
    }

    private TokenGrant prepareTokenGrant() {
        TokenGrant tokenGrant = new TokenGrant();
        tokenGrant.setGrantType(Const.OAuth.GRANT_TYPE_PASSWORD);
        tokenGrant.setUsername("username");
        tokenGrant.setPassword("password");
        tokenGrant.setClientId("client_id");
        tokenGrant.setClientSecret("client_password");
        return tokenGrant;
    }

}
