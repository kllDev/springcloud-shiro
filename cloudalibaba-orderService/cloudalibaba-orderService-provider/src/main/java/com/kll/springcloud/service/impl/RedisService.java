package com.kll.springcloud.service.impl;

import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.kll.springcloud.config.RedisExpireConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unchecked")
public class RedisService {
    private Logger logger = LoggerFactory.getLogger(RedisService.class);
    @SuppressWarnings("rawtypes")
    private RedisTemplate template;
    @SuppressWarnings({ "rawtypes" })
    private ListOperations listOpe;
    @SuppressWarnings("rawtypes")
    private ValueOperations valueOpe;
    @SuppressWarnings("rawtypes")
    private HashOperations hashOpe;
    @SuppressWarnings("rawtypes")
    private ZSetOperations zsetOpe;
    @SuppressWarnings("rawtypes")
    private SetOperations setOpe;
    @Autowired
    private RedisExpireConfig expireConfig;

    @SuppressWarnings("rawtypes")
    public RedisService(RedisTemplate template) {
        this.template = template;
        listOpe = template.opsForList();
        valueOpe = template.opsForValue();
        hashOpe = template.opsForHash();
        zsetOpe = template.opsForZSet();
        setOpe = template.opsForSet();
        this.template = template;
    }

    private String getKey(String head, String key) {
        return head + ":" + key;
    }

    public String get(String head, String key) {
        return (String) valueOpe.get(getKey(head, key));
    }

    public void set(String head, String key, String value) {
        valueOpe.set(getKey(head, key), value);
        // 设置过期时间
        expire(head, key);
    }

    /**
     * - 以配置文件为准,为reids设置对应的过期时间 - - @param head - @param key - @return
     */
    private boolean expire(String head, String key) {
        long times = expireConfig.getExpire4Header(head);
        if (times > 0) {
            try {
                return expire(head, key, times, TimeUnit.SECONDS);
            } catch (Exception e) {
                logger.warn("过期时间设置失败{head:" + head + ",key:" + key + "}。");
            }
        }
        return false;
    }

    /**
     * - 以传入的时间为准,设置相应的过期时间 - - @param head - @param key - @param timeout - @param
     * unit - @return
     */
    public boolean expire(String head, String key, long timeout, TimeUnit unit) {
        return template.expire(getKey(head, key), timeout, unit);
    }

    public boolean zadd(String head, String key, String member, double score) {
        boolean result = zsetOpe.add(getKey(head, key), member, score);
        expire(head, key);
        return result;
    }

    /**
     * - 按分数从小到大获取指定数量 - - @param head - @param key - @param start - @param end
     * - @return
     */
    public Set<String> rang(String head, String key, long start, long end) {
        return zsetOpe.range(getKey(head, key), start, end);
    }

    /**
     * - 按分数从大到小获取指定数量 - - @param head - @param key - @param start - @param end
     * - @return
     */
    public Set<String> reverseRange(String head, String key, long start, long end) {
        return zsetOpe.reverseRange(getKey(head, key), start, end);
    }

    /**
     * - 获取指定key下成员的分数 - - @param head - @param key - @param member - @return
     */
    public double score(String head, String key, String member) {
        return zsetOpe.score(getKey(head, key), member);
    }

    /**
     * - 获取排名--score低-->高 - - @param head - @param key - @param member - @return
     */
    public long rank(String head, String key, String member) {
        return zsetOpe.rank(getKey(head, key), member);
    }

    /**
     * - 获取排名--score高-->低 - - @param head - @param key - @param member - @return
     */
    public long reverseRank(String head, String key, String member) {
        return zsetOpe.reverseRank(getKey(head, key), member);
    }

    /**
     * - 获取指定起始位置的排行榜信息 - - @param head - @param key - @param start - @param end
     * - @return
     */
    public Set<ZSetOperations.TypedTuple<String>> reverseRangeWithScores(String head, String key, long start,
                                                                         long end) {
        return zsetOpe.reverseRangeWithScores(getKey(head, key), start, end);
    }

    /**
     * - 获取批量hashkey对应value对象json字符串 - - @param head - @param key - @param fields
     * - @param - @param < - @return
     */
    public List<String> hmget(String head, String key, Collection<?> fields) {
        if (CollectionUtils.isEmpty(fields)) {
            return null;
        }
        return hashOpe.multiGet(getKey(head, key), fields);
    }

    /**
     * - hset 操作 - - @param head - @param key - @param field - @param value
     */
    public void hset(String head, String key, String field, String value) {
        hashOpe.put(getKey(head, key), field, value);
        expire(head, key);
    }

    /**
     * - 获取所有的field - - @param head - @param key - @return
     */
    public Set<String> keys(String head, String key) {
        return hashOpe.keys(getKey(head, key));
    }

    /**
     * - 通过field获取value - - @param head - @param key - @param field - @return
     */
    public String hget(String head, String key, String field) {
        return (String) hashOpe.get(getKey(head, key), field);
    }

    /**
     * - 获取set里对应成员 - - @param head - @param key - @param - @return
     */
    public Set<String> smembers(String head, String key) {
        return setOpe.members(getKey(head, key));
    }

    /**
     * - 通过field获取value - - @param head - @param key - @param value - @return
     */
    public long sadd(String head, String key, String value) {
        long result = setOpe.add(getKey(head, key), value);
        expire(head, key);
        return result;
    }

    /**
     * - 在value后面追加值 - - @param head - @param key - @param addString
     */
    public int append(String head, String key, String addString) {
        return valueOpe.append(getKey(head, key), addString);
    }

    /**
     * - 加锁机制 true加锁成功 false加锁失败 - - @param head - @param key - @param lockValue
     */
    public boolean setnx(String head, String key, String lockValue) {
        boolean success = valueOpe.setIfAbsent(getKey(head, key), lockValue);
        if (success) {
            // 设置过期时间
            expire(head, key);
        }
        return success;
    }

    /**
     * - 加锁机制 true加锁成功 false加锁失败 - - @param head - @param key - @param lockValue
     */
    public boolean setnx(String head, String key, String lockValue,long time,TimeUnit timeUnit) {
        boolean success = valueOpe.setIfAbsent(getKey(head, key), lockValue);
        if (success) {
            // 设置过期时间
            expire(head, key,time,timeUnit);
        }
        return success;
    }

    /**
     * - 删除KEY - - @param head - @param key
     */
    public boolean delete(String head, String key) {
        return template.delete(getKey(head, key));
    }

    public long rightPush(String head, String key, String value) {
        return listOpe.rightPush(getKey(head, key), value);
    }

    /**
     *
     * - @param head - @param key - @param member - @return
     */
    public Double incrementScore(String head, String key, String member, Double score) {
        return zsetOpe.incrementScore(getKey(head, key), member, score);
    }

    /**
     * - @description: - @param head - @param key - @param map - @return: void
     */
    public void hmset(String head, String key, Map<? extends String, ? extends String> map) {
        if (!CollectionUtils.isEmpty((Collection) map)) {
            hashOpe.putAll(getKey(head, key), map);
            expire(head, key);
        }
    }

    /**
     *
     * - @param head - @param key - @param filed - @param value - @return
     */
    public Double Hincrby(String head, String key, String filed, Double value) {
        return hashOpe.increment(getKey(head, key), filed, value);
    }

    /**
     * - 删除hash数据结构
     *
     * - @param head - @param key
     */
    public Long hdel(String head, String key, String field) {
        return hashOpe.delete(getKey(head, key), field);
    }

    /**
     * - 判断可以是否存在 - @param head - @param key - @return
     */
    public boolean hasKey(String head, String key) {
        return template.hasKey(getKey(head, key));
    }

}
