package so.glad.environment;

/**
 * @author Cartoon Zhang
 * @since 2017/3/25 下午9:03
 */
public interface Configuration {

    String getConfig(String key);

    String getConfig(String key, String d4t);

}
