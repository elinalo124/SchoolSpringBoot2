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
public class Student {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDateTime birthday;
    private String email;

    /*
    @ManyToMany(mappedBy = "student")
    private List<Enrollment> enrollments;
    */

    @OneToMany(mappedBy = "student")
    private List<Enrollment> enrollments;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "STUDENT_APTITUDES",
            joinColumns = @JoinColumn(name = "studentId"),
            inverseJoinColumns = @JoinColumn(name = "aptitudeId"))
    private List<Aptitude> aptitudes;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", enrollments=" + enrollments +
                ", aptitudes=" + aptitudes +
                '}';
    }
}
