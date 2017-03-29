package so.glad.environment;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Cartoon Zhang
 * @since 2017/3/28 下午7:00
 */
public class StaticHashMapFactory implements SingletonFactory {

    private Map<Class, Map<String, Object>> map = new HashMap();

    private String DEFAULT_KEY = "";

    protected void init(){

    }

    private <T>Map<String, Object> defaultImplementation(Class<T> tClass, T t) {
        Map<String, Object> implementations = new HashMap();
        implementations.put(DEFAULT_KEY, t);
        return implementations;
    }

    @Override
    public <T> T get(Class<T> tClass) {
        return get(tClass, DEFAULT_KEY);
    }

    @Override
    public <T> T get(Class<T> tClass, String key) {
        if(map.isEmpty()) {
            this.init();
        }
        Map<String, Object> objectMap = map.get(tClass);
        if(objectMap == null || objectMap.isEmpty()) {
            T instance = null;
            try {
                instance = tClass.newInstance();
            } catch (Exception e) {
               e.printStackTrace();
            }
            map.put(tClass, defaultImplementation(tClass, instance));
            return instance;
        }
        return (T)objectMap.get(key);
    }
}
