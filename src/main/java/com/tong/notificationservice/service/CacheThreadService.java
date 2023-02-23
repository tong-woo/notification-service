package com.tong.notificationservice.service;

import java.util.Optional;

public interface CacheThreadService {
    Optional<String> findByKey(String key);

    boolean containsKey(String key);

    void cacheStore(String key, String ts);
}
