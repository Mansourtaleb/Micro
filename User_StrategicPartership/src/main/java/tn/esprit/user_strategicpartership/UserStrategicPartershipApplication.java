package tn.esprit.user_strategicpartership;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class UserStrategicPartershipApplication {

    public static void main(String[] args) {
        SpringApplication.run ( UserStrategicPartershipApplication.class, args );
    }

}
