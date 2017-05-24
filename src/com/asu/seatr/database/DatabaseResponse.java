package com.asu.seatr.database;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.type.IntegerType;

import com.asu.seatr.utils.GlobalConstants;
import com.asu.seatr.utils.Utils;

public class DatabaseResponse {
	
	public static void setAllStudentsIds(){
		
		SessionFactory sf_OPE_Class_25 = SessionFactoryUtil.getSessionFactory(GlobalConstants.OPE_Class_25);
		Session session25=sf_OPE_Class_25.openSession();
		
		SessionFactory sf_OPE_global = SessionFactoryUtil.getSessionFactory(GlobalConstants.OPE_global);
		Session sessionG=sf_OPE_global.openSession();
		
		//Students ID
		String studentsId_hql="SELECT distinct user_id as sid FROM student_response";
		Query sidQuery=session25.createQuery(studentsId_hql);
		List<Integer> sResult=sidQuery.list();
		int[] ids=new int[sResult.size()];
		int i=0;
		for (Integer id:sResult){
			ids[i++]=id;
		}
		Utils.setStudentsList(ids);
		for(int j=0;j<Utils.getStudentsList().length;j++){
			System.out.println(Utils.getStudent(j));
		}
		
		String kcs_hql="SELECT distinct id as kcid FROM knowledge_component";
		Query kcQuery=sessionG.createQuery(kcs_hql);
		List<Integer> kcResult=kcQuery.list();
		int[] kcs=new int[kcResult.size()];
		int kcIndex=0;
		for (Integer kcid:kcResult){
			kcs[i++]=kcid;
		}
		
		
		session25.close();
		sessionG.close();
		
		Utils.setKCsList(kcs);
		System.out.println("Student Ids "+sResult.size());
		
		System.out.println("KCs Ids "+kcResult.size());
		for(int j=0;j<Utils.getKCsList().length;j++){
			System.out.println(Utils.getKc(j));
		}
	}

}
