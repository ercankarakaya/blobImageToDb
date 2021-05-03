package com.ercan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;

@SpringBootApplication
public class SpringBootBlobImageToDbApplication {
   // private static Logger logger = LoggerFactory.getLogger(SpringBootBlobImageToDbApplication.class.getName());

    public static void main(String[] args) {
      //  logger.info("SPRING VERSION: " + SpringVersion.getVersion());
        SpringApplication.run(SpringBootBlobImageToDbApplication.class, args);

    }

}
