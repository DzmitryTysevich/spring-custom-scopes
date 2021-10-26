package scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadScope implements Scope {
    private final Map<String, Map<Thread, Object>> threadObjects = new ConcurrentHashMap<>();

    @Override
    public Object get(String s, ObjectFactory<?> objectFactory) {
        Thread currentThread = Thread.currentThread();
        if (!threadObjects.containsKey(s)) {
            threadObjects.put(s, new ConcurrentHashMap<>());
            threadObjects.get(s).put(currentThread, objectFactory.getObject());
        }
        return threadObjects.get(s).get(currentThread);
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