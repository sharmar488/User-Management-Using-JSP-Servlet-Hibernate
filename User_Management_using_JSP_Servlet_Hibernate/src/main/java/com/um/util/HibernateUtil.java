package com.um.util;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import com.um.model.User;



public class HibernateUtil {

	private static SessionFactory sessionfactory;
	
	public static SessionFactory getSessionFactory() {
		// TODO Auto-generated method stub
		if(sessionfactory==null) {
		
			try{
				Configuration config=new Configuration();
			   	Properties settings=new Properties();
			   	
			   	settings.put(Environment.DRIVER,"com.mysql.cj.jdbc.Driver" );
			   	settings.put(Environment.URL, "jdbc:mysql://localhost:3306/rahul_db");
			   	settings.put(Environment.USER, "root");
			   	settings.put(Environment.PASS, "admin2023");
			   	settings.put(Environment.DIALECT,"org.hibernate.dialect.MySQL8Dialect");
			   	settings.put(Environment.SHOW_SQL, true);
			   	settings.put(Environment.HBM2DDL_AUTO, "create-drop");
			   	settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS,"thread" );
			  
			   	config.setProperties(settings);
			   	config.addAnnotatedClass(User.class);
			   	
			   	ServiceRegistry serviceregistery=new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
			   	
			   	sessionfactory=config.buildSessionFactory(serviceregistery);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return sessionfactory;
	}

}
