package tn.esprit.workspace_workflow.service;



import org.springframework.stereotype.Service;
import tn.esprit.workspace_workflow.entity.Appointment;
import tn.esprit.workspace_workflow.repository.AppointmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository repository;

    public AppointmentService(AppointmentRepository repository) {
        this.repository = repository;
    }

    public List<Appointment> findAll() {
        return repository.findAll();
    }

    public Optional<Appointment> findById(String id) {
        return repository.findById(id);
    }

    public Appointment save(Appointment appointment) {
        return repository.save(appointment);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
