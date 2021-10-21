package scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadScope implements Scope {
    private final Map<String, Object> scopedObjects = Collections.synchronizedMap(new HashMap<>());
    private final ConcurrentHashMap<Thread, Object> threadObjects = new ConcurrentHashMap<>();

    @Override
    public Object get(String s, ObjectFactory<?> objectFactory) {
        Thread currentThread = Thread.currentThread();
        if (scopedObjects.get(s) == null) {
            scopedObjects.put(s, objectFactory.getObject());
            threadObjects.put(currentThread, scopedObjects.get(s));
        }
        return threadObjects.get(currentThread);
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