package com.elina.school.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Aptitude {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "aptitudes")
    private List<Course> courses;

    @ManyToMany(mappedBy = "aptitudes")
    private List<Professor> professors;

    @ManyToMany(mappedBy = "aptitudes")
    private List<Student> students;

    @Override
    public String toString() {
        return "Aptitude{" +
                "id=" + id +
                ", aptitude_name='" + name + '\'' +
                ", courses=" + courses +
                ", professors=" + professors +
                ", students=" + students +
                '}';
    }
}
