package com.hexaware.Hospital_Management_system;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


public class App 
{
    public static void main( String[] args )
    {
     SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
    	
    	//gives session object
    	Session session=sessionFactory.openSession();
    	
    	//if this is null then database is not connected
    	System.out.println(session);
    }
}
