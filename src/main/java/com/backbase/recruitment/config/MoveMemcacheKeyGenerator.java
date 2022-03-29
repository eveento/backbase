package com.backbase.recruitment.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.cache.interceptor.KeyGenerator;
import org.apache.commons.codec.digest.DigestUtils;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Collection;

import static com.backbase.recruitment.config.CacheConfiguration.KEYS_BP_WINNER;

@Log4j2
public final class MoveMemcacheKeyGenerator implements KeyGenerator {

    private static final int MAX_KEY_SIZE = 249;
    @Override
    public Object generate(Object target, Method method, Object... params) {
        if(params[0] instanceof Collection movies) {
            return hashKey(KEYS_BP_WINNER);
        }
        return null;
    }

    public static String hashKey(String key) {
        String hash = DigestUtils.sha1Hex(key);
        if (hash.length() > MAX_KEY_SIZE) {
            log.error(MessageFormat.format("Key generator must cut key because of generated key {0} is too long! Original key before hashing: {1}", hash, key));
        }
        return hash.substring(0, Math.min(hash.length(), MAX_KEY_SIZE));
    }
}
