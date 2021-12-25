package com.elina.school.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Status {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "status")
    private List<Course> courses;

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", status_name='" + name + '\'' +
                ", courses=" + courses +
                '}';
    }
}
