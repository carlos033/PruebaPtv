package com.example.demo;

import com.example.demo.util.PrecargaBD;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {"com.example.demo"})
public class DemoApplication {

    @Autowired
    private PrecargaBD myDatabaseSeeder;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    InitializingBean seedDatabase() {
        return () -> {
            myDatabaseSeeder.precargarBaseDeDatos();
        };
    }
}
