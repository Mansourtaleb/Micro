package tn.esprit.workspace_workflow.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import tn.esprit.workspace_workflow.entity.Appointment;
import tn.esprit.workspace_workflow.entity.Calendar;
import tn.esprit.workspace_workflow.service.CalendarService;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/calendars")
@CrossOrigin(origins = "http://localhost:4200")
public class CalendarController {

    private final CalendarService calendarService;

    @PostMapping("/create")
    public Calendar createCalendar(@RequestParam("month") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate month) {
        return calendarService.createCalendar(month);
    }

    @GetMapping("/appointments")
    public List<Appointment> getAppointmentsForMonth(@RequestParam("month") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate month) {
        return calendarService.getAppointmentsForMonth(month);
    }

    @GetMapping("/all")
    public List<Calendar> getAllCalendars() {
        return calendarService.getAppointmentsForMonthByAll();
    }

    @PostMapping("/appointments")
    public Appointment createAppointmentForMonth(@RequestBody Appointment appointment,
                                                 @RequestParam("month") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate month) {
        return calendarService.createAppointmentForMonth(appointment, month);
    }

    @DeleteMapping("/appointments/{id}")
    public void deleteAppointment(@PathVariable String id) {
        calendarService.deleteAppointment(id);
    }
}
