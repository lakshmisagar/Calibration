/**
 * 
 */
package com.asu.seatr.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author Lakshmisagar Kusnoor created on May 15, 2017
 *
 */
public class Utils {

	 private static int[] mKC = new int[GlobalConstants.total_KCs];
	// private static BigDecimal[] mInitialMastery = new
	// BigDecimal[GlobalConstants.total_KCs];
	// private static BigDecimal[] mLearn = new
	// BigDecimal[GlobalConstants.total_KCs];
	//private static BigDecimal[] mSlip = new BigDecimal[GlobalConstants.total_Questions];
	//private static BigDecimal[] mGuess = new BigDecimal[GlobalConstants.total_Questions];
	private static int[] studentsList = new int[GlobalConstants.total_Students];
	private static int[] questionsList = new int[GlobalConstants.total_Questions];

	// Datastructure to implement question id question
	static HashMap<Integer, Integer> id_question_map = new HashMap<Integer,Integer>();
		
	// Datastructure to implement Last[Student]
	static HashMap<Integer, Integer> last_map = new HashMap<Integer,Integer>();
	
	// Datastructure to implement Kc InitialMater and Learn
	static HashMap<Integer, HashMap<Integer, BigDecimal>> kc_initialMastery_Learn_map = new HashMap<Integer, HashMap<Integer, BigDecimal>>();

	// Datastructure to implement Question Qmatrix Slip and Guess
	static HashMap<Integer, HashMap<Integer, String>> Q_QM_Slip_Guess_map = new HashMap<Integer, HashMap<Integer, String>>();

	// Datastructure to implement Question(S,A,Q)
	static HashMap<Integer, HashMap<Integer, Integer>> question_SA_Map = new HashMap<Integer, HashMap<Integer, Integer>>();

	// Datastructure to implement QuestionMatrix(Q)
	static HashMap<Integer, ArrayList<Integer>> qMatrix_map = new HashMap<Integer, ArrayList<Integer>>();

	// Datastructure to implement Answer(S,A,Q)
	static HashMap<Integer, HashMap<Integer, Integer>> answer_SA_Map = new HashMap<Integer, HashMap<Integer, Integer>>();

	// TODO change the logic from 3 maps to 1 map here in utils like Questionand
	// Answer
	// Datastructure to implement BEST
	static HashMap<Integer, BigDecimal> best_innerBestMap = new HashMap<Integer, BigDecimal>();
	static HashMap<Integer, HashMap<Integer, BigDecimal>> best_innerKcBestMap = new HashMap<Integer, HashMap<Integer, BigDecimal>>();
	static HashMap<Integer, HashMap<Integer, HashMap<Integer, BigDecimal>>> best_outerStudentKcMap = new HashMap<Integer, HashMap<Integer, HashMap<Integer, BigDecimal>>>();

	// Datastructure to implement FORWARD
	static HashMap<Integer, BigDecimal> forward_innerForwardMap = new HashMap<Integer, BigDecimal>();
	static HashMap<Integer, HashMap<Integer, BigDecimal>> forward_innerKcBestMap = new HashMap<Integer, HashMap<Integer, BigDecimal>>();
	static HashMap<Integer, HashMap<Integer, HashMap<Integer, BigDecimal>>> forward_outerStudentKcMap = new HashMap<Integer, HashMap<Integer, HashMap<Integer, BigDecimal>>>();

	// Datastructure to implement BACKWARD
	static HashMap<Integer, BigDecimal> backward_innerBackwardMap = new HashMap<Integer, BigDecimal>();
	static HashMap<Integer, HashMap<Integer, BigDecimal>> backward_innerKcBestMap = new HashMap<Integer, HashMap<Integer, BigDecimal>>();
	static HashMap<Integer, HashMap<Integer, HashMap<Integer, BigDecimal>>> backward_outerStudentKcMap = new HashMap<Integer, HashMap<Integer, HashMap<Integer, BigDecimal>>>();

	/*
	 * Students List
	 */

	public static int[] getStudentsList() {
		return studentsList;
	}

	public static void setStudentsList(int[] studentsList) {
		Utils.studentsList = studentsList;
	}
	/*
	 * Student
	 */

	public static int getStudent(int index) {
		return studentsList[index];
	}

	public static void setStudent(int index, int studentid) {
		Utils.studentsList[index] = studentid;
	}


	/*
	 * QMatrix
	 */
	public static void setQuestionMatrix(int mQuestion, int kc) {
		ArrayList<Integer> list = qMatrix_map.get(mQuestion);
		//System.out.println();
		//System.out.println("setQuestionMatrix :"+mQuestion+" : "+kc+" : "+list);
		if (list == null) {
			list = new ArrayList<Integer>();
			list.add(kc);
		} else {
				list.add(kc);
		}
		qMatrix_map.put(mQuestion, list);
		//System.out.println("count :"+qMatrix_map.size());
	}

	public static ArrayList<Integer> getQuestionMatrix(int mQuestion) {
		ArrayList<Integer> list = qMatrix_map.get(mQuestion);
		//TODO remove below condition once you match with correct kc from table
		if(list==null) {
			list = new ArrayList<Integer> ();
			list.add(getKc(0));
		}
		return list;
	}

	/*
	 * Forward
	 */
	public static void updateForward(int S, int K, int A, BigDecimal forwardfillingValue) {
		// TODO implement forward filling
		// System.out.println("set Forward - S:"+S+" K:"+K+" A:"+A+"
		// ="+forwardfillingValue);
		forward_innerForwardMap.put(A, forwardfillingValue);
		forward_innerKcBestMap.put(K, forward_innerForwardMap);
		forward_outerStudentKcMap.put(S, forward_innerKcBestMap);
	}

	public static BigDecimal getForward(int S, int K, int A) {
		HashMap<Integer, HashMap<Integer, BigDecimal>> Kcmap = forward_outerStudentKcMap.get(S);
		HashMap<Integer, BigDecimal> forwardmap = Kcmap.get(K);
		return forwardmap.get(A);
	}

	/*
	 * Backward
	 */
	public static void updateBackward(int S, int K, int A, BigDecimal backwardfillingValue) {
		// System.out.println("updateBackward S:"+S+" K:"+K+" A:"+A+" -
		// "+backwardfillingValue);
		// TODO implement backward filling
		backward_innerBackwardMap.put(A, backwardfillingValue);
		backward_innerKcBestMap.put(K, backward_innerBackwardMap);
		backward_outerStudentKcMap.put(S, backward_innerKcBestMap);
	}

	public static BigDecimal getBackward(int S, int K, int A) {
		HashMap<Integer, HashMap<Integer, BigDecimal>> Kcmap = backward_outerStudentKcMap.get(S);
		HashMap<Integer, BigDecimal> backwardmap = Kcmap.get(K);
		// System.out.println("getBackward S:"+S+" K:"+K+" A:"+A+" -
		// "+backwardmap.get(A));
		return backwardmap.get(A);
	}

	/*
	 * Best
	 */
	public static void updateBest(int S, int K, int A, BigDecimal bestValue) {
		// TODO implement forward filling
		best_innerBestMap.put(A, bestValue);
		best_innerKcBestMap.put(K, best_innerBestMap);
		best_outerStudentKcMap.put(S, best_innerKcBestMap);
	}

	public static BigDecimal getBest(int S, int K, int A) {
		HashMap<Integer, HashMap<Integer, BigDecimal>> Kcmap = best_outerStudentKcMap.get(S);
		HashMap<Integer, BigDecimal> bestmap = Kcmap.get(K);
		return bestmap.get(A);
	}

	/*
	 * Answer
	 */
	public static void setAnswer(int s, HashMap<Integer, Integer> answer_AC_Map) {
		answer_SA_Map.put(s, answer_AC_Map);
	}

	public static int getAnswer(int S, int A) {
		HashMap<Integer, Integer> innerAC_map = answer_SA_Map.get(S);
		return innerAC_map.get(A);
	}

	/*
	 * Question
	 */
	public static void setQuestion(int s, HashMap<Integer, Integer> question_AQ_Map) {
		question_SA_Map.put(s, question_AQ_Map);
	}
	public static int getQuestion(int S, int A) {
		HashMap<Integer, Integer> innerAQ_map = question_SA_Map.get(S);
		//System.out.println("get SQA :"+S+" "+innerAQ_map.get(A)+" "+A);
		return innerAQ_map.get(A);
	}


	
	
	/*
	 * KCs List
	 */
	public static int[] getKcList() {
		return mKC;
	}

	public static void setKcList(int[] questionList) {
		mKC = questionList;
	}

	public static int getKc(int index) {
		return mKC[index];
	}

	public static void setKc(int index, int questionid) {
		mKC[index] = questionid;
	}
	//*************MAP  - Kc IM L *********************************  
	public static void setKcMap(int Kc) {
			HashMap<Integer, BigDecimal> map = new HashMap<Integer, BigDecimal>();
			kc_initialMastery_Learn_map.put(Kc, map);
	}

	public static void setInitialMasteryMap(int Kc, BigDecimal value) {
		kc_initialMastery_Learn_map.get(Kc).put(GlobalConstants.IM, value.setScale(20, RoundingMode.HALF_UP));
	}

	public static BigDecimal getInitialMasteryMap(int Kc) {
		return kc_initialMastery_Learn_map.get(Kc).get(GlobalConstants.IM);
	}

	public static void setLearnMap(int Kc, BigDecimal value) {
		kc_initialMastery_Learn_map.get(Kc).put(GlobalConstants.Learn, value.setScale(20, RoundingMode.HALF_UP));
	}

	public static BigDecimal getLearnMap(int Kc) {
		return kc_initialMastery_Learn_map.get(Kc).get(GlobalConstants.Learn);
	}

	/*
	 * Question List
	 */
	public static int[] getQuestionsList() {
		return questionsList;
	}

	public static void setQuestionsList(int[] questionList) {
		Utils.questionsList = questionList;
	}

	public static int getQuestion(int index) {
		//System.out.println("getQuestion :"+index+"  ");
		return questionsList[index];
	}

	public static void setQuestion(int index, int questionid) {
		Utils.questionsList[index] = questionid;
	}

	
	//*************MAP **/*******************************  

	//**************class question and its id*****************
	public static void setClassIdQuestion(int index, int questionid) {
		id_question_map.put(index, questionid);
	}

	public static int getClassIdQuestion(int index) {
		return id_question_map.get(index);
	}

	//************* Q S G QM **/*******************************  
	public static void setQuestionMap(int question) {
			HashMap<Integer, String> map = new HashMap<Integer, String>();
			Q_QM_Slip_Guess_map.put(question, map);
	}
	
	
	public static void setSlipMap(int question, BigDecimal value) {
		System.out.println("setSlipMap :"+question+"  "+value);
		Q_QM_Slip_Guess_map.get(question).put(GlobalConstants.Slip, value.toString());
	}

	public static BigDecimal getSlipMap(int question) {
		return new BigDecimal(Q_QM_Slip_Guess_map.get(question).get(GlobalConstants.Slip));
	}

	public static void setGuessMap(int question, BigDecimal value) {
		Q_QM_Slip_Guess_map.get(question).put(GlobalConstants.Guess, value.toString());
	}

	public static BigDecimal getGuessMap(int question) {
		return new BigDecimal(Q_QM_Slip_Guess_map.get(question).get(GlobalConstants.Guess));
	}

	public static void setQMatrixMap(int question , ArrayList<Integer> list) {
		//System.out.println("setQMatrixMap :"+question+" "+list);
		String listString = "";
		for (Integer kc : list){
		    listString += kc + "\t";
		}
		Q_QM_Slip_Guess_map.get(question).put(GlobalConstants.QMatrix, listString);
	}
	
	public static ArrayList<Integer> getQMatrixMap(int question) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		String s = Q_QM_Slip_Guess_map.get(question).get(GlobalConstants.QMatrix);
		String[] arr= s.split("\t");
		for(int i=0;i<arr.length;i++){
			list.add(Integer.parseInt(arr[i]));
		}
		return list;
	}
	
	/*
	 * Last
	 */
	public static int getLast(int mStudentId) {
		if(!last_map.containsKey(mStudentId)){
			return 0;
		}
		return last_map.get(mStudentId);
	}
	
	public static void setLast(int mStudentId, int questionsCount) {
		last_map.put(mStudentId, questionsCount);
	}

	
}
