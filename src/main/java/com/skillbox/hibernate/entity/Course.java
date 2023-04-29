package com.skillbox.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity // этот класс представляет таблицу в базе
@Table(name = "Courses") // меняем имя как в таблице(должно быть одинаковым)
public class Course {
    @Id // первичный ключ
    @GeneratedValue(strategy = GenerationType.IDENTITY) // стратегия генерации(изменение в ходе работы) первичного ключа
    private int id;

    private String name;

    private int duration;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum")
    private Type type;

    private String description;

    @Column(name = "students_сount") // меняем имя как в столбце(должно быть одинаковым)
    private int studentsCount;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    Teacher teacher;

    private int price;

    @Column(name = "price_per_hour")
    private float pricePerHour;


    @ManyToMany
    @JoinTable(name = "subscriptions",
    joinColumns = {@JoinColumn(name = "student_id")},
    inverseJoinColumns = {@JoinColumn(name = "curse_id")})
    List<Student> student;


}
