package com.config.transaction;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-09-12 13:41
 */
//@Configuration
@Getter
@Setter
public class TransactionManagerConfiguration {
    @Autowired
    private DataSource dataSource;
    @Value("${aplus.base.transaction.timeout:-1}")
    private int timeout;
    @Value("cat.transaction.monitor.enabled:false")
    private boolean enabledMonitor;

    @Primary
    @Bean(name = "transactionManager")
    @ConditionalOnProperty(value = "cat.transaction.monitor", name = "enabled", havingValue = "true", matchIfMissing = false)
    public DataSourceTransactionManager monitoringTransactionManager() {
        DataSourceTransactionManager manager = getDataSOurceTransactionManager();
        manager.setDataSource(dataSource);
        manager.setDefaultTimeout(timeout);
        return manager;
    }

    private DataSourceTransactionManager getDataSOurceTransactionManager() {
        if (enabledMonitor) {
            return new DataSourceTransactionManager() {
                @Override
                protected void prepareForCommit(DefaultTransactionStatus status) {
                    // TODO
                    if (TransactionSynchronizationManager.isSynchronizationActive()) {
//                        TransactionSynchronizationManager.registerSynchronization();
                    }
                }
            };
        } else {
            return new DataSourceTransactionManager();
        }
    }

}
