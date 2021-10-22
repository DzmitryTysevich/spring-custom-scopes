package scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ThreeTimesScope implements Scope {
    private final Map<String, Object> scopedObjects = Collections.synchronizedMap(new HashMap<>());
    private final int INTERVAL = 3;
    private int objectCounter;

    @Override
    public Object get(String s, ObjectFactory<?> objectFactory) {
        if (objectCounter == INTERVAL) {
            scopedObjects.clear();
            objectCounter = 0;
        }
        if (!scopedObjects.containsKey(s)) {
            scopedObjects.put(s, objectFactory.getObject());
        }
        objectCounter++;
        return scopedObjects.get(s);
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
        return "threeTimes";
    }
}
