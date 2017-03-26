package so.glad.oauth.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Cartoon Zhang
 * @since 2017/3/25 下午9:05
 */
public class PropertiesConfiguration implements Configuration {

    private Logger logger = LoggerFactory.getLogger(PropertiesConfiguration.class);

    private Properties properties = new Properties();

    public PropertiesConfiguration() {
        try {
            properties.load(this.getClass().getResourceAsStream("/application.properties"));
        } catch (IOException e) {
            logger.error("Load config error.", e);
        }
    }

    @Override
    public String getConfig(String key) {
        return properties.get(key).toString();
    }

    @Override
    public String getConfig(String key, String d4t) {
        return properties.getProperty(key, d4t);
    }
}
