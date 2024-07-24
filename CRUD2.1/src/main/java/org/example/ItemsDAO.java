package org.example;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class ItemsDAO {

    public void saveItem(Items item) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(item);
            transaction.commit();
        }
    }

    public Items getItemById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Items.class, id);
        }
    }

    public void updateItem(Items item) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(item);
            transaction.commit();
        }
    }

    public void deleteItem(Items item) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(item);
            transaction.commit();
        }
    }

    public List<Items> getAllItems() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Items", Items.class).list();
        }
    }
}