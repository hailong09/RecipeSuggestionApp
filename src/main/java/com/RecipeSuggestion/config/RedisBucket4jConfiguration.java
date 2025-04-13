package com.RecipeSuggestion.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy;
import io.github.bucket4j.distributed.proxy.ClientSideConfig;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.function.Supplier;

@Configuration
public class RedisBucket4jConfiguration {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Value("${app.redis.host}")
    public String redisHost;

    @Value("${app.redis.password}")
    public String redisPassword;

    @Value("${app.redis.port}")
    public String redisPort;

    private RedisClient redisClient() {
        LOG.debug("redis://default:{}@{}:{}", redisPassword, redisHost, redisPort);
        return RedisClient.create("redis://default:"+ redisPassword +"@"+ redisHost +":" + redisPort);
    }

    @Bean
    public ProxyManager<String> proxyManager() {
        ClientSideConfig clientSideConfig = ClientSideConfig.getDefault()
                .withExpirationAfterWriteStrategy(
                        ExpirationAfterWriteStrategy.basedOnTimeForRefillingBucketUpToMax(Duration.ofMinutes(1))
                );

        StatefulRedisConnection<String, byte[]> redisConnection = redisClient()
                .connect(RedisCodec.of(StringCodec.UTF8, ByteArrayCodec.INSTANCE));

        return LettuceBasedProxyManager.builderFor(redisConnection).withClientSideConfig(clientSideConfig).build();
    }

    @Bean
    public Supplier<BucketConfiguration> bucketConfiguration() {
        return () -> BucketConfiguration.builder()
                .addLimit(Bandwidth
                        .builder()
                        .capacity(100)
                        .refillIntervally(100, Duration.ofMinutes(1))
                        .build())
                .build();
    }
}
