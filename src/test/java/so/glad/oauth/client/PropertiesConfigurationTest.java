package so.glad.oauth.client;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import so.glad.environment.Configuration;
import so.glad.environment.PropertiesConfiguration;
import so.glad.oauth.Config;
import so.glad.oauth.Const;

/**
 * @author Cartoon Zhang
 * @since 2017/3/26 下午5:48
 */
public class PropertiesConfigurationTest extends TestCase {

    static Configuration configuration = new PropertiesConfiguration();

    @Test
    public void testGetConfig() {
        Assert.assertEquals(Const.OAuth.GRANT_TYPE_CLIENT, configuration.getConfig(Config.Key.GRANT_TYPE));
        Assert.assertNull("It's really null", configuration.getConfig("some.other.config.key"));
    }

    @Test
    public void testGetConfigDefault() {
        Assert.assertEquals("client_id_123456", configuration.getConfig(Config.Key.CLIENT_ID, "client_id_7890"));
        Assert.assertEquals("soglad", configuration.getConfig("some.other.config.key", "soglad"));
    }
}
