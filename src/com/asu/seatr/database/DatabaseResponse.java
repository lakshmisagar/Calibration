package com.asu.seatr.database;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.type.IntegerType;

import com.asu.seatr.opetest.models.question_knowledge_component;
import com.asu.seatr.opetest.models.student_response;
import com.asu.seatr.utils.GlobalConstants;
import com.asu.seatr.utils.Utils;

public class DatabaseResponse {

	public static void setAllStudentsData() {

		SessionFactory sf_OPE_Class_25 = SessionFactoryUtil.getSessionFactory(GlobalConstants.OPE_Class_25);
		Session session25 = sf_OPE_Class_25.openSession();

		// Students ID
		String studentsId_hql = "SELECT distinct user_id as sid FROM student_response";
		Query sidQuery = session25.createQuery(studentsId_hql);
		List<Integer> sResult = sidQuery.list();
		GlobalConstants.total_Students = sResult.size();
		// Questions
		String question_hql = "SELECT id as ids ,question_id as qid FROM class_question";
		Query qidQuery = session25.createQuery(question_hql);
		List<Object[]> qResult = qidQuery.list();
		GlobalConstants.total_Questions = qResult.size();
		// Last
		Query last_hql = session25.createQuery("SELECT user_id as sid, correct as c, class_question_id as cqid FROM student_response");
		List<Object[]> lastResult = (List<Object[]>) last_hql.list();

		/*
		 * String stuResp_hql = "FROM student_response where user_id =1"; Query
		 * stuRespQuery = session25.createQuery(stuResp_hql);
		 * System.out.println(
		 * "PRINTING--------------------------------------------------------------"
		 * ); //System.out.println(stuRespQuery.list()); System.out.println(
		 * "PRINTING--------------------------------------------------------------"
		 * ); List<student_response> stuRespResult = stuRespQuery.list();
		 */
		session25.disconnect();
		session25.close();

		SessionFactory sf_OPE_global = SessionFactoryUtil.getSessionFactory(GlobalConstants.OPE_global);
		Session sessionG = sf_OPE_global.openSession();
		// Kc
		String kcs_hql = "SELECT id as kcid FROM knowledge_component";
		Query kcQuery = sessionG.createQuery(kcs_hql);
		List<Integer> kcResult = kcQuery.list();
		GlobalConstants.total_KCs = kcResult.size();
		// QMatrix
		String qMatrix_hql = "FROM question_knowledge_component";
		Query qMQuery = sessionG.createQuery(qMatrix_hql);
		List<question_knowledge_component> qMResult = qMQuery.list();
		sessionG.disconnect();
		sessionG.close();

		// Students ID
		int[] ids = new int[sResult.size()];
		int i = 0;
		for (Integer id : sResult) {
			ids[i++] = id;
		}
		Utils.setStudentsList(ids);
		System.out.println(
				"total Student-------------------------------------------- :" + GlobalConstants.total_Students);
		/*
		 * for (int j = 0; j < Utils.getStudentsList().length; j++) {
		 * System.out.println(Utils.getStudent(j)); }
		 */

		// Questions
		int qResultCount=0;
		for (Object[] result : qResult) {
			Integer id = (Integer) result[0];
			Integer Q = (Integer) result[1];
			Utils.setQuestion(qResultCount, Q);
			Utils.setQuestionMap(Q);
			Utils.setClassIdQuestion(id, Q);
			qResultCount++;
		}
		
		System.out.println(
				"total Questions--------------------------------------------:" + GlobalConstants.total_Questions);
		for (int j = 0; j < GlobalConstants.total_Questions; j++) {
			System.out.println(Utils.getQuestion(j));
		}

		// Kc
		for (i = 0; i < kcResult.size(); i++) {
			// System.out.println(kcResult.get(i));
			Utils.setKc(i, kcResult.get(i));
			Utils.setKcMap(kcResult.get(i));
		}
		System.out.println("total Kcs-----------------------------" + GlobalConstants.total_KCs);
		for (int j = 0; j < GlobalConstants.total_KCs; j++) {
			// System.out.println(Utils.getKc(j));
		}

		// QMatrix
		for (i = 0; i < qMResult.size(); i++) {
			int q = qMResult.get(i).getQuestion_id();
			int kc = qMResult.get(i).getKnowledge_component_id();
			// System.out.println("question_knowledge_component " + q + " " +
			// kc);
			Utils.setQuestionMatrix(q, kc);
		}
		System.out.println(GlobalConstants.total_Questions + " count " + i);
		System.out.println("QMATRIX-------------------------------------------");
		for (int j = 0; j < GlobalConstants.total_Questions; j++) {
			int q = Utils.getQuestion(j);
			Utils.setQMatrixMap(q, Utils.getQuestionMatrix(q));
			// System.out.println(q +" { "+ Utils.getQMatrixMap(q)+" }");
		}
		// LAST
		HashMap<Integer, Integer> question_AQ_Map = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> answer_AC_Map = new HashMap<Integer, Integer>();
		int y = 0;
		System.out.println("LAST--------------------------");
		for (Object[] result : lastResult) {
			y++;
			Integer id = (Integer) result[0];
			Integer correct = (Integer) result[1];
			int count = Utils.getLast(id);
			count++;
			Utils.setLast(id, count);
			int A = count;
			int Q;
			if (result[2] == null) {
				Q = 0;
			} else {
				Q = Utils.getClassIdQuestion((Integer) result[2]);
			}
			// System.out.println("SAQ "+y+" :"+id+" "+A+" "+Q);
			answer_AC_Map.put(A,correct);
			Utils.setAnswer(id, answer_AC_Map);
			question_AQ_Map.put(A, Q);
			Utils.setQuestion(id, question_AQ_Map);
		}
		
		System.out.println(" L A S T -------------------------------------------------");
		for (i = 0; i < GlobalConstants.total_Students; i++) {
			// System.out.println(Utils.getStudent(i)+"
			// "+Utils.getLast(Utils.getStudent(i)));
		}

	}

}