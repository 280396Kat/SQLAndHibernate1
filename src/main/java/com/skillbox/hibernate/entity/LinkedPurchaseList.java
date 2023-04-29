package com.skillbox.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Data // создает гет, сет, конструктор, иквелс , хэш-код
@Entity
public class LinkedPurchaseList {
    @EmbeddedId
    private LinkedPurchaseKey id;

    @Column(name = "student_id")
    private int studentId;

    @Column(name = "course_id")
    private int courseId;

}
