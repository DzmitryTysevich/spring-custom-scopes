package scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadScope implements Scope {
    private final Map<String, ThreadLocal<Object>> threadObjects = new ConcurrentHashMap<>();

    @Override
    public Object get(String s, ObjectFactory<?> objectFactory) {
        if (!threadObjects.containsKey(s)) {
            ThreadLocal<Object> threadLocal = new ThreadLocal<>();
            threadLocal.set(objectFactory.getObject());
            threadObjects.put(s, threadLocal);
        }
        return threadObjects.get(s).get();
    }

    @Override
    public Object remove(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void registerDestructionCallback(String s, Runnable runnable) {
    }

    @Override
    public Object resolveContextualObject(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getConversationId() {
        return "thread";
    }
}