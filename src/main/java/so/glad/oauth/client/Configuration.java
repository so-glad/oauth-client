package so.glad.oauth.client;

/**
 * @author Cartoon Zhang
 * @since 2017/3/25 下午9:03
 */
public interface Configuration {

    String getConfig(String key);

    String getConfig(String key, String d4t);

    javax.cache.configuration.Configuration<String, AccessToken> cacheConfiguration =
            new javax.cache.configuration.Configuration<String, AccessToken>() {
                @Override
                public Class<String> getKeyType() {
                    return String.class;
                }

                @Override
                public Class<AccessToken> getValueType() {
                    return AccessToken.class;
                }

                @Override
                public boolean isStoreByValue() {
                    return false;
                }
            };

}
