package com.tong.notification.service;

import java.util.Optional;

public interface CachedThreadService {
    Optional<String> findByKey(String key);

    boolean containsKey(String key);

    void cacheStore(String key, String ts);
}
