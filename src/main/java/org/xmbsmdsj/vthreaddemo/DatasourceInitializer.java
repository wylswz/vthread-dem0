package org.xmbsmdsj.vthreaddemo;

import com.mysql.cj.jdbc.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DatasourceInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger logger = LoggerFactory.getLogger(DatasourceInitializer.class);

    private static final String INIT_SQL = """
            CREATE TABLE IF NOT EXISTS kv (id BIGINT PRIMARY KEY AUTO_INCREMENT, `key` varchar(255) UNIQUE NOT NULL, `value` TEXT);
            """;

    private final DataSource dataSource;

    public DatasourceInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.info("initializing database");
        try(var stmt = dataSource.getConnection().prepareStatement(INIT_SQL)) {
            var res = stmt.execute();
        } catch (SQLException e) {
            logger.error("failed to initialize database", e);
        }
    }
}
