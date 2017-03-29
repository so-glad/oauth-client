package so.glad.environment;

/**
 * @author Cartoon Zhang
 * @since 2017/3/28 下午6:58
 */
public interface SingletonFactory {

    SingletonFactoryDelegate singleton = new SingletonFactoryDelegate(new StaticHashMapFactory());

    <T>T get(Class<T> tClass);

    <T>T get(Class<T> tClass, String key);
}
