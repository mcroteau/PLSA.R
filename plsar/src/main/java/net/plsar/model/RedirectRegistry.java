package net.plsar.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RedirectRegistry {
    ConcurrentMap<String, Map<String, Object>> registry;

    public ConcurrentMap<String, Map<String, Object>> getRegistry() {
        return registry;
    }

    public void setRegistry(ConcurrentMap<String, Map<String, Object>> registry) {
        this.registry = registry;
    }

    public RedirectRegistry() {
        this.registry = new ConcurrentHashMap<>(0, 6, 240);
    }
}
