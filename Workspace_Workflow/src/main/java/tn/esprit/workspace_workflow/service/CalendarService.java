package tn.esprit.workspace_workflow.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.workspace_workflow.entity.Appointment;
import tn.esprit.workspace_workflow.entity.Calendar;
import tn.esprit.workspace_workflow.repository.AppointmentRepository;
import tn.esprit.workspace_workflow.repository.CalendarRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CalendarService {

    private CalendarRepository calendarRepository;

    private AppointmentRepository appointmentRepository;

    public Calendar createCalendar(LocalDate month) {
        Calendar calendar = new Calendar();
        calendar.setMonth(month);
        return calendarRepository.save(calendar);
    }

    public List<Appointment> getAppointmentsForMonth(LocalDate month) {
        return appointmentRepository.findByDate(month);
    }

    public List<Calendar> getAppointmentsForMonthByAll() {
        return calendarRepository.findAll () ;
    }


    public Appointment createAppointmentForMonth(Appointment appointment, LocalDate month) {
        Calendar calendar = calendarRepository.findByMonth(month).get(0); // Associer à un calendrier existant
        appointment.setCalendar(calendar);
        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(String appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }
}
