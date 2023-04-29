package com.skillbox.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity // этот класс представляет таблицу в базе
@Table(name = "PurchaseList")
public class PurchaseList {

    @EmbeddedId
    private PurchaseKey id;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "course_name")
    private String courseName;

    private int price;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime subscriptionData;

    @ManyToOne
    @JoinColumn(name = "student_name", referencedColumnName = "name")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_name", referencedColumnName = "name")
    private Course course;




}
