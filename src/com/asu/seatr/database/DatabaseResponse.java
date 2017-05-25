package com.asu.seatr.database;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.type.IntegerType;

import com.asu.seatr.opetest.models.question_knowledge_component;
import com.asu.seatr.utils.GlobalConstants;
import com.asu.seatr.utils.Utils;

public class DatabaseResponse {

	public static void setAllStudentsIds() {

		SessionFactory sf_OPE_Class_25 = SessionFactoryUtil.getSessionFactory(GlobalConstants.OPE_Class_25);
		Session session25 = sf_OPE_Class_25.openSession();

		// Students ID
		String studentsId_hql = "SELECT distinct user_id as sid FROM student_response";
		Query sidQuery = session25.createQuery(studentsId_hql);
		List<Integer> sResult = sidQuery.list();
		int[] ids = new int[sResult.size()];
		int i = 0;
		for (Integer id : sResult) {
			ids[i++] = id;
		}
		GlobalConstants.total_Students = sResult.size();
		Utils.setStudentsList(ids);
		for (int j = 0; j < Utils.getStudentsList().length; j++) {
			System.out.println(Utils.getStudent(j));
		}

		// Questions
		String question_hql = "SELECT question_id as qid FROM class_question";
		Query qidQuery = session25.createQuery(question_hql);
		List<Integer> qResult = qidQuery.list();
		int[] qIds = new int[qResult.size()];
		i = 0;
		for (Integer id : qResult) {
			qIds[i++] = id;
		}
		GlobalConstants.total_Questions = qResult.size();
		Utils.setQuestionsList(qIds);
		for (int j = 0; j < Utils.getQuestionsList().length; j++) {
			System.out.println(Utils.getQuestion(j));
		}

		session25.close();

		SessionFactory sf_OPE_global = SessionFactoryUtil.getSessionFactory(GlobalConstants.OPE_global);
		Session sessionG = sf_OPE_global.openSession();

		// Kc
		String kcs_hql = "SELECT id as kcid FROM knowledge_component";
		Query kcQuery = sessionG.createQuery(kcs_hql);
		List<Integer> kcResult = kcQuery.list();
		for (int i=0;i<kcResult.size();i++) {
			System.out.println(kcResult.get(i));
			Utils.setKc(i, kcResult.get(i));
		}
		GlobalConstants.total_KCs = kcResult.size();
		System.out.println("KCs Ids " + GlobalConstants.total_KCs);
		for (int j = 0; j < GlobalConstants.total_KCs; j++) {
			System.out.println(Utils.getKc(j));
		}

		// QMatrix
		String qMatrix_hql = "FROM question_knowledge_component";
		Query qMQuery = sessionG.createQuery(qMatrix_hql);
		List<question_knowledge_component> qMResult = qMQuery.list();
		for(int i=0;i<qMResult.size();i++){
			Utils.setQuestionMatrix(qMResult.get(i).getQuestion_id(), qMResult.get(i).getKnowledge_component_id());
		}
		for (int j = 0; j < GlobalConstants.total_Questions; j++) {
			System.out.println("ss"+Utils.getQuestionMatrix(Utils.getQuestion(j)));
		}
		
		sessionG.close();
	}

}
