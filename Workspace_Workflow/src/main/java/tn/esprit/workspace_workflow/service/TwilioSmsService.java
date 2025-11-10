package tn.esprit.workspace_workflow.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.twilio.exception.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TwilioSmsService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    public String sendSms(String to, String message) {
        try {
            // Vérification du format du numéro de téléphone
            if (!to.matches("^\\+\\d{1,15}$")) {
                return "Numéro de téléphone invalide, veuillez vérifier le format.";
            }

            // Envoi du SMS
            Message sms = Message.creator(
                    new PhoneNumber(to),
                    new PhoneNumber(twilioPhoneNumber),
                    message
            ).create();

            return "SMS envoyé avec SID : " + sms.getSid();

        } catch (ApiException e) {
            // Gestion des erreurs Twilio
            return "Erreur lors de l'envoi du SMS : " + e.getMessage();
        } catch (Exception e) {
            // Gestion d'autres exceptions
            return "Une erreur s'est produite lors de l'envoi du SMS : " + e.getMessage();
        }
    }
}
