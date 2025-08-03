package com.example.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.model.Announcement;

@Repository
public class AnnouncementDaoImpl implements AnnouncementDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Announcement announcement) {
        sessionFactory.getCurrentSession().saveOrUpdate(announcement);
    }

    @Override
    public Announcement get(Long id) {
        return sessionFactory.getCurrentSession().get(Announcement.class, id);
    }

    @Override
    public List<Announcement> list(int page, int size) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Announcement order by publishDate desc", Announcement.class)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .list();
    }

    @Override
    public long count() {
        return sessionFactory.getCurrentSession()
                .createQuery("select count(*) from Announcement", Long.class)
                .uniqueResult();
    }

    @Override
    public void delete(Long id) {
        Announcement announcement = get(id);
        if (announcement != null) {
            sessionFactory.getCurrentSession().delete(announcement);
        }
    }
}

