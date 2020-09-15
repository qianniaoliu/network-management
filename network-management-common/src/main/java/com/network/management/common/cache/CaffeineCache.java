package com.network.management.common.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * Caffeine cache
 *
 * @author yyc
 * @date 2020/9/16 00:48
 */
@Component
@Slf4j
public class CaffeineCache {
    /**
     * 默认失效时间
     */
    private static final int TIME_OUT = 60 * 60;
    /**
     * 最大对象数量
     */
    private static final int MAX_SIZE = 2000;
    /**
     * Caffeine cache对象
     */
    private Cache<String, String> cache;

    @PostConstruct
    public void init() {
        cache = Caffeine.newBuilder()
                .expireAfterWrite(TIME_OUT, TimeUnit.SECONDS)
                .maximumSize(MAX_SIZE)
                .build();
        log.info("Caffeine Cache init success.");
    }

    /**
     * 获取存储的ProductAll对象
     *
     * @param key
     */
    public String get(String key) {
        return cache.getIfPresent(key);
    }

    /**
     * 存储对象
     */
    public void put(String key, String value) {
        cache.put(key, value);
    }
}
