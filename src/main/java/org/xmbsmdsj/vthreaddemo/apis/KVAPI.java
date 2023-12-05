package org.xmbsmdsj.vthreaddemo.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.web.bind.annotation.*;
import org.xmbsmdsj.vthreaddemo.repo.KVRepository;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/kv")
public class KVAPI {

    @Autowired
    KVRepository kvRepository;

    @GetMapping(path = "/mysql/{key}")
    public String getValue(@PathVariable String key) {
        return kvRepository.findByKey(key).getValue();
    }


    public record RedisSetReq (String key, String value){
        public byte[] keyBytes() { return key.getBytes(); }
        public byte[] valueBytes() { return value.getBytes(); }
    }

    @Autowired
    private JedisConnectionFactory factory;

    @PutMapping(path = "/redis")
    public void redisSet(@RequestBody RedisSetReq req) {
        try (var conn = factory.getConnection()) {
            conn.stringCommands().set(req.keyBytes(), req.valueBytes());
        }
    }

    @GetMapping(path = "/redis/{key}")
    public String getRedisValue(@PathVariable String key) {
        try (var conn = factory.getConnection()) {
            return Optional.ofNullable(conn.stringCommands().get(key.getBytes()))
                    .map(String::new)
                    .orElseThrow(()->new RuntimeException("key not found"));
        }
    }

}
