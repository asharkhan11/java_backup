package com.ashar.profileManager;

import com.ashar.profileManager.constants.DatabaseDetails;
import com.ashar.profileManager.constants.EnvStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;

@Slf4j
@SpringBootApplication
public class FirstBootApplication implements CommandLineRunner {

    //	@Value("${banking.application.details.name}")
//	private String bankName;
//
//	@Value("${banking.application.details.ifsc}")
//	private String ifsc;
//	@Autowired
//	private BankingDetails bankingDetails;


    @Autowired
    private EnvStatus envStatus;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private DatabaseDetails databaseDetails;

    public static void main(String[] args) {
        SpringApplication.run(FirstBootApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        try (Connection con = dataSource.getConnection()) {
            log.warn("Application running in {} environment", envStatus.getENV());
            log.warn("Database connection details :");
            log.info("url : {}",databaseDetails.getUrl());
            log.info("username : {}",databaseDetails.getUsername());
            log.info("driver class name : {}",databaseDetails.getDriverClassName());
        } catch (Exception e) {
            throw new IllegalStateException("Database connection failed!!!");
        }
    }
}
