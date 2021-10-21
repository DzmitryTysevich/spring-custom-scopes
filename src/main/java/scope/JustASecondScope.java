package scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class JustASecondScope implements Scope {
    private final Map<String, Object> scopedObjects = Collections.synchronizedMap(new HashMap<>());
    private long lastTime = System.currentTimeMillis();
    private long timeInterval;

    @Override
    public Object get(String s, ObjectFactory<?> objectFactory) {
        long currentTime = System.currentTimeMillis();
        getTimeInterval(currentTime);

        if (timeInterval >= 1000) {
            scopedObjects.clear();
            timeInterval = 0;
        }
        if (scopedObjects.get(s) == null) {
            scopedObjects.put(s, objectFactory.getObject());
        }
        lastTime = System.currentTimeMillis();
        return scopedObjects.get(s);
    }

    private void getTimeInterval(long currentTime) {
        timeInterval += currentTime - lastTime;
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