package org.xmbsmdsj.vthreaddemo;

import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;

import javax.sql.DataSource;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration(proxyBeanMethods = false)
public class ApplicationConfiguration {

    @Profile("virtual")
    @Configuration
    public static class VirtualThreadConfiguration {
        @Bean(TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME)
        public AsyncTaskExecutor asyncTaskExecutor() {
            return new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
        }

        @Bean
        TomcatProtocolHandlerCustomizer<?> protocolHandlerCustomizer() {
            return protocolHandler -> {
                protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
            };
        }
    }

    @Bean
    DatasourceInitializer datasourceInitializer(DataSource dataSource) {
        return new DatasourceInitializer(dataSource);
    }
}
