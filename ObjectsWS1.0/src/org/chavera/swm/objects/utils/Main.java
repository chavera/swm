package org.chavera.swm.objects.utils;
import java.util.List;

import org.chavera.swm.objects.model.Sample;
import org.hibernate.Query;
import org.hibernate.Session;

public class Main {

	public static void main(String[] args) {
	        System.out.println("Maven + Hibernate + MySQL");
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        Query query = session.createQuery("from Sample"); 
	        List list = query.list();
	        for(Object o : list){
	        	Sample s = (Sample)o;
	        	System.out.println(s.getName());
	        }
	}

}
