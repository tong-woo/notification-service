package com.tong.notification.service.impl;

import com.tong.notification.service.CachedThreadService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Data
public class CachedThreadServiceDefault implements CachedThreadService {

    private final Map<String, String> threadMap;

    @Override
    public Optional<String> findByKey(String key) {
        return Optional.ofNullable(this.getThreadMap().get(key));
    }

    @Override
    public boolean containsKey(String key) {
        return this.getThreadMap().containsKey(key);
    }

    @Override
    public void cacheStore(String key, String ts) {
        this.getThreadMap().put(key, ts);
    }

    @Scheduled(cron = "${schedule.cron.daytime}")
    public void dailyPurge() {
        this.getThreadMap().clear();
    }
}
