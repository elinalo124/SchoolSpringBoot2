package com.elina.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Enrollment {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer grade;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="student")
    private Student student;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "course")
    private Course course;

    /*
    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "ENROLLMENT_STUDENTS",
            joinColumns = @JoinColumn(name = "enrollment_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> students;
     */

    /*
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "course")
    private Professor professor;
     */

    /*
    @ManyToOne
    @JoinColumn(name = "status_id")
    @JsonIgnore
    private Status status;
     */

    @Override
    public String toString() {
        return "Enrollment{" +
                "id=" + id +
                ", grade=" + grade +
                ", students=" + student +
                ", course=" + course +
                '}';
    }
}
