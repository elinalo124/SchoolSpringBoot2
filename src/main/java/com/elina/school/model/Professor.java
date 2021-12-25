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
public class Professor {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDateTime birthday;
    private String email;

    @OneToMany(mappedBy = "professor")
    private List<Course> courses;

    /*
    @OneToMany(mappedBy = "professor")
    private List<Enrollment> enrollments;
     */

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "PRODESSOR_APTITUDES",
            joinColumns = @JoinColumn(name = "professorId"),
            inverseJoinColumns = @JoinColumn(name = "aptitudeId"))
    private List<Aptitude> aptitudes;

    @Override
    public String toString() {
        return "Professor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", courses=" + courses +
                ", aptitudes=" + aptitudes +
                '}';
    }
}
