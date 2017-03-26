package so.glad.oauth.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import so.glad.oauth.Config;
import so.glad.oauth.Const;

import javax.annotation.Resource;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cartoon Zhang
 * @since 2017/3/24 下午10:00
 */
public abstract class OAuthClientImpl implements OAuthClient {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Setter
    @Resource
    private HttpClient httpClient;
    @Setter
    @Resource
    private ObjectMapper objectMapper;
    @Setter
    @Resource
    private TokenGrantProvider tokenGrantProvider;
    @Setter
    @Resource
    private Configuration configuration;
    @Setter
    @Resource
    private CacheManager cacheManager;

    private String rootURI;

    private List<Header> headerList = new ArrayList();

    private void checkDependencies() {
        if (httpClient == null) {
            httpClient = HttpClientBuilder.create().build();
        }
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        if (configuration == null) {
            configuration = new PropertiesConfiguration();
        }
        if (tokenGrantProvider == null) {
            tokenGrantProvider = new TokenGrantProviderImpl()
                    .setConfig(this.configuration);
        }
        if (cacheManager == null) {
            cacheManager = Caching.getCachingProvider().getCacheManager();
        }
        if (cacheManager.getCache(Const.OAuth.CACHE_OAUTH, String.class, AccessToken.class) == null) {
            cacheManager.createCache(Const.OAuth.CACHE_OAUTH, Configuration.cacheConfiguration);
        }
        this.configAsEnv();
    }

    private void configAsEnv() {
        String isTest = configuration.getConfig(Config.Key.TEST_ENV);
        if ("false".equals(isTest) || "0".equals(isTest) || "off".equals(isTest)) {
            this.rootURI = "https://topup.zeofat.com";
        } else {
            this.rootURI = "https://topup.glad.so";
        }
        headerList.add(new BasicHeader(Const.OAuth.HEADER_ACCEPT,
                ContentType.APPLICATION_JSON.getMimeType())
        );
    }

    private HttpUriRequest post(String path, String body) {
        HttpPost post = new HttpPost(this.rootURI + path);
        post.setHeaders(this.headerList.toArray(new Header[this.headerList.size()]));
        if (body != null && !"".equals(body.trim())) {
            post.setEntity(new StringEntity(body, ContentType.APPLICATION_JSON));
        }
        return post;
    }

    private HttpUriRequest get(String path) {
        HttpUriRequest get = new HttpGet(this.rootURI + path);
        get.setHeaders(this.headerList.toArray(new Header[this.headerList.size()]));
        return get;
    }

    protected <T> T post(String uriPath, String body, Class<T> tClass) {
        try {
            HttpResponse response = httpClient.execute(post(uriPath, body));
            HttpEntity httpEntity = response.getEntity();
            return objectMapper.readValue(httpEntity.getContent(), tClass);
        } catch (IOException e) {
            logger.error("Execute http post error, path: " + uriPath + ", payload: " + body, e);
            return null;
        }
    }

    protected <T> T get(String uriPath, Class<T> tClass) {
        try {
            HttpResponse response = httpClient.execute(get(uriPath));
            HttpEntity httpEntity = response.getEntity();
            return objectMapper.readValue(httpEntity.getContent(), tClass);
        } catch (IOException e) {
            logger.error("Execute http get error, path: " + uriPath, e);
            return null;
        }
    }

    protected void exchangeAccessToken(String... credentials) {
        /* Find accessToken from cache.*/
        this.checkDependencies();

        Cache<String, AccessToken> cache =
                cacheManager.getCache(Const.OAuth.CACHE_OAUTH, String.class, AccessToken.class);
        AccessToken accessToken = cache.get(Const.OAuth.CACHE_ACCESS_TOKEN);

        if (accessToken != null && accessToken.beforeExpiredTime() > 60 * 60 * 1000) {
            return;
        } else if (accessToken != null &&
                accessToken.getRefreshToken() != null && !"".equals(accessToken.getRefreshToken())) {
            accessToken = accessToken(tokenGrantProvider.refreshGrant(accessToken.getRefreshToken()));
            cache.put(Const.OAuth.CACHE_ACCESS_TOKEN, accessToken);
        } else {
            accessToken = accessToken(tokenGrantProvider.defaultGrant(credentials));
            cache.put(Const.OAuth.CACHE_ACCESS_TOKEN, accessToken);
        }
        this.headerList.add(new BasicHeader(Const.OAuth.HEADER_AUTHORIZATION,
                Const.OAuth.PREFIX_BAERER + accessToken.getAccessToken())
        );
    }

    public AccessToken accessToken(TokenGrant tokenGrant) {
        return post("/oauth/token", tokenGrant.toJson(), AccessToken.class);
    }
}
