package scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class JustASecondScope implements Scope {
    private final Map<String, Object> scopedObjects = Collections.synchronizedMap(new HashMap<>());
    private final long SECOND_IN_MILLIS = 1000L;
    private long lastTime = System.currentTimeMillis();
    private long objectsCreationTime;

    @Override
    public Object get(String s, ObjectFactory<?> objectFactory) {
        long currentTime = System.currentTimeMillis();
        calculateObjectsCreationTime(currentTime);

        if (isaCreationPerSecond()) {
            scopedObjects.clear();
            objectsCreationTime = 0L;
        }
        if (!scopedObjects.containsKey(s)) {
            scopedObjects.put(s, objectFactory.getObject());
        }
        lastTime = System.currentTimeMillis();
        return scopedObjects.get(s);
    }

    private void calculateObjectsCreationTime(long currentTime) {
        objectsCreationTime += getObjectCreationTime(currentTime);
    }

    private boolean isaCreationPerSecond() {
        return objectsCreationTime >= SECOND_IN_MILLIS;
    }

    private long getObjectCreationTime(long currentTime) {
        return currentTime - lastTime;
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
        return "justASecond";
    }
}