package com.skillbox.hibernate;

import com.skillbox.hibernate.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.Service;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.Entity;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.awt.*;
import java.util.List;

public class Main {
    public static StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder().
            configure("hibernate.cfg.xml").build(); // ходит

    public static Metadata metadata = new MetadataSources(standardServiceRegistry).getMetadataBuilder().build(); // читает

    public static SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build(); // управляет


    public static void main(String[] args) {

        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<PurchaseList> purchaseListCriteriaQuery = criteriaBuilder.createQuery(PurchaseList.class);
            Root<PurchaseList> purchaseListRoot = purchaseListCriteriaQuery.from(PurchaseList.class);
            List<PurchaseList> purchaseLists = session.createQuery(purchaseListCriteriaQuery.select(purchaseListRoot)).getResultList();
            for (PurchaseList tmp : purchaseLists) {
                String nameCourse = tmp.getCourseName();
                String nameStudent = tmp.getStudentName();
                CriteriaBuilder builder = session.getCriteriaBuilder();
                CriteriaQuery<Course> courseCriteriaQuery = criteriaBuilder.createQuery(Course.class);
                Root<Course> courseRoot = courseCriteriaQuery.from(Course.class);
                CriteriaQuery<Student> studentCriteriaQuery = criteriaBuilder.createQuery(Student.class);
                Root<Student> studentRoot = studentCriteriaQuery.from(Student.class);
                List<Course> courseList = session.createQuery(courseCriteriaQuery.select(courseRoot)
                        .where(builder.equal(courseRoot.<String>get("name"), nameCourse))).getResultList();
                List<Student> studentList = session.createQuery(studentCriteriaQuery.select(studentRoot)
                        .where(builder.equal(studentRoot.<String>get("name"), nameStudent))).getResultList();

                LinkedPurchaseList linkedPurchaseList = new LinkedPurchaseList();
                linkedPurchaseList.setId(new LinkedPurchaseKey(studentList.get(0).getId(), courseList.get(0).getId()));
                session.persist(linkedPurchaseList);
            }
            transaction.commit();
            //purchaseLists.stream()
            //        .map(purchaseList -> {
            //            String nameCourse = purchaseList.getCourseName();
            //            String nameStudent = )}

        } catch (Exception e) {
            transaction.rollback();
        } finally {
            sessionFactory.close();
        }

    }
}

