package in.ashar.handleFiles.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "in.ashar.handleFiles.repositories.mysql",
        entityManagerFactoryRef = "sqlEntityManager",
        transactionManagerRef = "sqlTransactionManager"
)
public class MySqlConfiguration {



    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.mysql")
    public DataSourceProperties sqlDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean
    public DataSource sqlDatasource(@Qualifier("sqlDataSourceProperties") DataSourceProperties dataSourceProperties){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        driverManagerDataSource.setUrl(dataSourceProperties.getUrl());
        driverManagerDataSource.setUsername(dataSourceProperties.getUsername());
        driverManagerDataSource.setPassword(dataSourceProperties.getPassword());

        return driverManagerDataSource;
    }

    @Bean
    public JpaVendorAdapter sqlJpaVendorAdapter(){
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(true);
        jpaVendorAdapter.setGenerateDdl(true);
        return jpaVendorAdapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean sqlEntityManager(@Qualifier("sqlDatasource") DataSource dataSource, @Qualifier("sqlJpaVendorAdapter") JpaVendorAdapter jpaVendorAdapter){

        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        localContainerEntityManagerFactoryBean.setDataSource(dataSource);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        localContainerEntityManagerFactoryBean.setPackagesToScan("in.ashar.handleFiles.entities.mysql");

        return localContainerEntityManagerFactoryBean;

    }

    @Bean
    public PlatformTransactionManager sqlTransactionManager(@Qualifier("sqlEntityManager") LocalContainerEntityManagerFactoryBean factoryBean){
        assert factoryBean.getObject() != null;
        return new JpaTransactionManager(factoryBean.getObject());
    }

}
