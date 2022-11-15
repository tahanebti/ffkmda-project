package com.tahanebti.ffkmda.config;

import java.io.Serializable;
import java.time.Duration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import redis.clients.jedis.Jedis;

import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

//@Configuration
//@AutoConfigureAfter(RedisAutoConfiguration.class)
//@ConditionalOnProperty(value="resolved.cache.enabled", havingValue = "true", matchIfMissing = true)
//@PropertySource("classpath:/redis.properties")
//@EnableCaching
public class RedisConfig {

	//@Autowired private CacheManager cacheManager;

	@Value("${spring.redis.host}")
	private String redisHost;

	@Value("${spring.redis.port}")
	private int redisPort;
	
	@Value("${resolved.cache.ttl:30}")
	private long ttlInMinutes;

	@Bean
	public RedisTemplate<String, Serializable> redisCacheTemplate(LettuceConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Serializable> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		
		   //RedisObjectMapper:
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(new RedisObjectMapper());
		
      
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();
		//	template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

		
		return template;
	}
	
	
	
	

	@Bean
	public CacheManager cacheManager(RedisConnectionFactory factory) {
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
		
		
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();

		   //RedisObjectMapper:
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(new RedisObjectMapper());
        
        
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
        		 .disableCachingNullValues()
                 .entryTtl(Duration.ofMinutes(ttlInMinutes))
        		.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
        		.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));

				
		RedisCacheManager redisCacheManager = RedisCacheManager.builder(factory).cacheDefaults(redisCacheConfiguration)
				.build();
		
		redisCacheManager.setTransactionAware(false);
		
		
		return redisCacheManager;
	}

	@PostConstruct
	public void clearCache() {
		System.out.println("In Clear Cache");
		Jedis jedis = new Jedis(redisHost, redisPort, 10000);
		jedis.flushAll();
		jedis.close();
	}
	


	 class RedisObjectMapper extends ObjectMapper {
	        private static final long serialVersionUID = 1L;
	        public RedisObjectMapper() {
	            super();
	            this.configure(MapperFeature.USE_ANNOTATIONS, false);
	            this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	            this.enableDefaultTyping(DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
	            this.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
	            this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	            this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	            this.findAndRegisterModules();

	        }
	    }

}
