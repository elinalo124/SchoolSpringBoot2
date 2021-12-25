package com.elina.school.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Course {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseName;
    private String courseDescription;
    private int minGrade;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "statusId")
    @JsonIgnore
    private Status status;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "COURSE_APTITUDES",
            joinColumns = @JoinColumn(name = "courseId"),
            inverseJoinColumns = @JoinColumn(name = "aptitudeId"))
    private List<Aptitude> aptitudes;

    @OneToMany(mappedBy = "course")
    private List<Enrollment> enrollments;

    @ManyToOne
    @JoinColumn(name = "professorId")
    @JsonIgnore
    private Professor professor;

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", minGrade=" + minGrade +
                ", status=" + status +
                ", aptitudes=" + aptitudes +
                ", enrollments=" + enrollments +
                '}';
    }
}
