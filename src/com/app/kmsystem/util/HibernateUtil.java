package com.app.kmsystem.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * File: com.app.kmsystem.util.HibernateUtil.java
 * 
 * Project:	kmsystem
 * Author : Reno Natalino
 * Created: Nov 9, 2010, 2:29:27 PM
 */

public class HibernateUtil {
    private static final SessionFactory sessionFactory;
    
    static {
        try {
            Configuration configuration = new Configuration().configure("config/hibernate.cfg.xml");
            // Create session factory from session spring
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.out.println("Initial SessionFactory creation failed. " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void close(){
        getSessionFactory().close();
    }
}
