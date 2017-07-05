package com.pms.app.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by arjun on 7/5/2017.
 */

@Configuration
public class CacheConfig {
    @Bean
    public CacheManager getEhCacheManager() {
        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager(getEhCacheFactory().getObject());
        ehCacheCacheManager.getCacheManager().addCache("customers");
        ehCacheCacheManager.getCacheManager().addCache("machines");
        ehCacheCacheManager.getCacheManager().addCache("designs");
        ehCacheCacheManager.getCacheManager().addCache("sizes");
        ehCacheCacheManager.getCacheManager().addCache("colors");
        ehCacheCacheManager.getCacheManager().addCache("currencies");
        ehCacheCacheManager.getCacheManager().addCache("knitters");
        ehCacheCacheManager.getCacheManager().addCache("locations");
        ehCacheCacheManager.getCacheManager().addCache("prints");
        ehCacheCacheManager.getCacheManager().addCache("yarns");
        ehCacheCacheManager.getCacheManager().addCache("prices");
        return ehCacheCacheManager;
    }

    @Bean
    public EhCacheManagerFactoryBean getEhCacheFactory() {
        EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
        factoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        factoryBean.setShared(true);
        return factoryBean;
    }
}
