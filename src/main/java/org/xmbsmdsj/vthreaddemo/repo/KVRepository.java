package org.xmbsmdsj.vthreaddemo.repo;

import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.xmbsmdsj.vthreaddemo.models.KV;

public interface KVRepository extends CrudRepository<KV, Long> {
    KV findByKey(String key);
}
