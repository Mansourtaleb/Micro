package tn.esprit.workspace_workflow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "calendars")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Calendar {

    @Id
    private String id = UUID.randomUUID().toString();
    @Column(name = "month_value")
    private LocalDate month;

    @OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;

}
