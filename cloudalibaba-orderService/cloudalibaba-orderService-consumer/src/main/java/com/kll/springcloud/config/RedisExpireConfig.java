package com.kll.springcloud.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@ConfigurationProperties(prefix = "spring.redis.expire")
public class RedisExpireConfig {
    /**
     * - redis中header对应的过期时间
     */
    private Map<String, Long> headerExpire;

    public Map<String, Long> getHeaderExpire() {
        return headerExpire;
    }

    public void setHeaderExpire(Map<String, Long> headerExpire) {
        this.headerExpire = headerExpire;
    }

    /**
     * - 获取对应header设置的过期时间 - - @param header - @return
     */
    public long getExpire4Header(String header) {
        if (!CollectionUtils.isEmpty(headerExpire)) {
            Long result = headerExpire.get("[" + header + "]");
            if (null == result) {
                result = 0L;
            }
            return result;
        }
        return 0L;
    }
}
