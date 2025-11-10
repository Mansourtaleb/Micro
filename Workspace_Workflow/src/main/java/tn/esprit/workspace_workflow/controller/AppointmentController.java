package tn.esprit.workspace_workflow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.workspace_workflow.entity.Appointment;
import tn.esprit.workspace_workflow.service.AppointmentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
@CrossOrigin(origins = "http://localhost:4200")
public class AppointmentController {

    private final AppointmentService service;

    @GetMapping("/all")
    public List<Appointment> getAllAppointments() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment appointment) {
        return service.save(appointment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable String id, @RequestBody Appointment updated) {
        return service.findById(id).map(existing -> {
            existing.setTitle(updated.getTitle());
            existing.setDate(updated.getDate());
            existing.setStartTime(updated.getStartTime());
            existing.setEndTime(updated.getEndTime());
            // Map Calendar reference (entity) instead of calendarId string
            if (updated.getCalendar() != null && updated.getCalendar().getId() != null) {
                // Attach by id without fetching (optional: you can fetch to validate existence)
                tn.esprit.workspace_workflow.entity.Calendar c = new tn.esprit.workspace_workflow.entity.Calendar();
                c.setId(updated.getCalendar().getId());
                existing.setCalendar(c);
            } else {
                existing.setCalendar(null);
            }
            return ResponseEntity.ok(service.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
