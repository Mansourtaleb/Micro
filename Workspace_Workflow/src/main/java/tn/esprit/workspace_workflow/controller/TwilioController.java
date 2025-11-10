package tn.esprit.workspace_workflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.workspace_workflow.service.TwilioSmsService;

@RestController
@RequestMapping("/api/sms")
public class TwilioController {

    @Autowired
    private TwilioSmsService twilioSmsService;

    @PostMapping("/send")
    public ResponseEntity<String> sendSms(@RequestParam String to, @RequestParam String message) {
        // Appeler le service pour envoyer l'SMS
        String result = twilioSmsService.sendSms(to, message);

        // Vérifier si l'envoi a été réussi ou si une erreur est survenue
        if (result.contains("SMS envoyé avec SID")) {
            return new ResponseEntity<>(result, HttpStatus.OK); // Succès
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST); // Erreur
        }
    }
}
