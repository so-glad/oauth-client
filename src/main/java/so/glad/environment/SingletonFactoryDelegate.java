package so.glad.environment;

import lombok.Setter;

/**
 * @author Cartoon Zhang
 * @since 2017/3/28 下午8:31
 */
public class SingletonFactoryDelegate implements SingletonFactory {
    @Setter
    private SingletonFactory delegate;

    SingletonFactoryDelegate(StaticHashMapFactory delegate) {
        this.delegate = delegate;
    }

    @Override
    public <T> T get(Class<T> tClass) {
        return delegate.get(tClass);
    }

    @Override
    public <T> T get(Class<T> tClass, String key) {
        return delegate.get(tClass, key);
    }
}
