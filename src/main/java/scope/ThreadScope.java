package scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.core.NamedThreadLocal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadScope implements Scope {
    private final ThreadLocal<Map<String, Object>> threadScope = new NamedThreadLocal<>("ThreadScope") {
        @Override
        protected Map<String, Object> initialValue() {
            return new ConcurrentHashMap<>();
        }
    };

    @Override
    public Object get(String s, ObjectFactory<?> objectFactory) {
        if (!threadScope.get().containsKey(s)) {
            threadScope.get().put(s, objectFactory.getObject());
        }
        return threadScope.get().get(s);
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