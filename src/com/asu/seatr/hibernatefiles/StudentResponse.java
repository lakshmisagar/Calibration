package com.asu.seatr.hibernatefiles;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.type.IntegerType;

public class StudentResponse {
	
	public static String[] setAllStudentsIds(){
		String hql="SELECT distinct user_id as sid FROM student_response";
		SessionFactory sf = SessionFactoryUtil.getSessionFactory();
		Session session=sf.openSession();
		Query mQuery=session.createQuery(hql).setParameter("sid", IntegerType.INSTANCE);
		List<Integer> result=mQuery.list();
		String[] ids=new String[result.size()];
		int i=0;
		for (Integer id:result){
			System.out.println(String.valueOf(id));
			ids[i++]=String.valueOf(id);
		}
		session.close();
		return ids;
	}
}
