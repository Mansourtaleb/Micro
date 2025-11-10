package tn.esprit.notification_message_collaboration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
/////////CAB/////
@SpringBootApplication
@EnableMongoAuditing
public class NotificationMessageCollaborationApplication {

    public static void main(String[] args) {
        SpringApplication.run ( NotificationMessageCollaborationApplication.class, args );
    }

}
