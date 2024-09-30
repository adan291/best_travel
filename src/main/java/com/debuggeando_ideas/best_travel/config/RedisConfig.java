package com.debuggeando_ideas.best_travel.config;

import com.debuggeando_ideas.best_travel.util.constant.CacheConstants;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;


@Configuration
@EnableCaching
@Slf4j
@EnableScheduling
public class RedisConfig {

    @Value("${cache.redis.address}")
    private String serverAddress;

    @Value("${cache.redis.password}")
    private String serverPassword;

    @Bean
    public RedissonClient redissonClient() {
        var config = new Config();

        // Configuración de servidor único
        config.useSingleServer()
                .setAddress(serverAddress)
                .setPassword(serverPassword);

        return Redisson.create(config);
    }

    @Bean
    public CacheManager cacheManager(RedissonClient redissonClient) {
        // Aquí puedes configurar el TTL (time to live) y maxIdleTime
        var configs = Map.of(
                CacheConstants.FLY_CACHE_NAME, new CacheConfig(60 * 1000, 30 * 1000),  // Ejemplo: 1 min TTL, 30 seg idle
                CacheConstants.HOTEL_CACHE_NAME, new CacheConfig(60 * 1000, 30 * 1000)  // Configura según tu necesidad
        );
        return new RedissonSpringCacheManager(redissonClient, configs);
    }

    @CacheEvict(cacheNames = {
            CacheConstants.FLY_CACHE_NAME,
            CacheConstants.HOTEL_CACHE_NAME
    }, allEntries = true)
    @Scheduled(cron = CacheConstants.SCHEDULED_RESET_CACHE)
    @Async
    public void deleteCache() {
        log.info("Limpieza de cache ejecutada");
    }
}
