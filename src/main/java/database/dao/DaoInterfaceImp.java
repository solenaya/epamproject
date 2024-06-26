package database.dao;

import database.util.HibernateUtil;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Olga on 13.08.2017.
 */
public abstract  class DaoInterfaceImp<T, PK extends Serializable> implements DaoInterface<T,PK> {

    private Class<T> type;

    public DaoInterfaceImp(Class<T> type){
        this.type = type;
    }

    @Override
    public T getById(PK id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        T el = s.get(type,id);
        s.close();
        return el;
    }

    @Override
    public List<T> getAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<T> list = s.createQuery("from " + type.getName() + "").list();
        s.close();
        return list;
    }

    @Override
    public PK save(T t) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        PK id= (PK) s.save(t);
        s.getTransaction().commit();
        s.close();
        return id;
    }

    @Override
    public void update(T t) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        s.update(t);
        s.getTransaction().commit();
        s.close();
    }

    @Override
    public void deleteById(PK id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        T entity = s.load(type,id);
        s.delete(entity);
        s.getTransaction().commit();
        s.close();
    }
}
