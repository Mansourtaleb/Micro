package tn.esprit.workspace_workflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.workspace_workflow.entity.Calendar;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, String> {
    List<Calendar> findByMonth(LocalDate month);
}
