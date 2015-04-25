package mega_classes;

import java.util.HashMap;
import java.util.Map;

public class MegaStudent {
	
	private int id;
	private String netId;
	private Map<Integer, Integer> preMt1Answers; // Map of TA who answered a question to number of questions answered by that TA
	private Map<Integer, Integer> mt1ToMt2Answers;
	private Map<Integer, Integer> mt2ToFinalAnswers;
	private int questionsAnsweredPreMt1;
	private int questionsAnsweredMt1ToMt2;
	private int questionsAnsweredMt2ToFinal;
	private int totalQuestionsAnswered; // Total of all 3 maps
	private double mt1Grade;
	private double mt2Grade;
	private double finalExamGrade;
	private double overallGrade;
	
	public MegaStudent(int id, String netId, double mt1Grade, double mt2Grade, double finalExamGrade, double overallGrade) {
		this.id = id;
		this.netId = netId;
		preMt1Answers = new HashMap<Integer, Integer>();
		mt1ToMt2Answers = new HashMap<Integer, Integer>();
		mt2ToFinalAnswers = new HashMap<Integer, Integer>();
		this.mt1Grade = mt1Grade;
		this.mt2Grade = mt2Grade;
		this.finalExamGrade = finalExamGrade;
		this.overallGrade = overallGrade;
	}
	
	public int getId() { return id; }
	public String getNetId() { return netId; }
	public Map<Integer, Integer> getPreMt1Answers() { return preMt1Answers; }
	public Map<Integer, Integer> getMt1ToMt2Answers() { return mt1ToMt2Answers; }
	public Map<Integer, Integer> getMt2ToFinalAnswers() { return mt2ToFinalAnswers; }
	public int getQuestionsAnsweredPreMt1() { return questionsAnsweredPreMt1; }
	public int getQuestionsAnsweredMt1ToMt2() { return questionsAnsweredMt1ToMt2; }
	public int getQuestionsAnsweredMt2ToFinal() { return questionsAnsweredMt2ToFinal; }
	public int getTotalQuestionsAnswered() { return totalQuestionsAnswered; }
	public double getMt1Grade() { return mt1Grade; }
	public double getMt2Grade() { return mt2Grade; }
	public double getFinalExamGrade() { return finalExamGrade; }
	public double getOverallGrade() { return overallGrade; }
	
	public void addPreMt1Answer(int answererId) {
		if (preMt1Answers.containsKey(answererId)) {
			preMt1Answers.put(answererId, preMt1Answers.get(answererId) + 1);
		} else {
			preMt1Answers.put(answererId, 1);
		}
	}
	
	public void addMt1ToMt2Answer(int answererId) {
		if (mt1ToMt2Answers.containsKey(answererId)) {
			mt1ToMt2Answers.put(answererId, mt1ToMt2Answers.get(answererId) + 1);
		} else {
			mt1ToMt2Answers.put(answererId, 1);
		}
	}
	
	public void addMt2ToFinalAnswer(int answererId) {
		if (mt2ToFinalAnswers.containsKey(answererId)) {
			mt2ToFinalAnswers.put(answererId, mt2ToFinalAnswers.get(answererId) + 1);
		} else {
			mt2ToFinalAnswers.put(answererId, 1);
		}
	}
	
	public void finalizeTotalQuestionsAnswered() {
		questionsAnsweredPreMt1 = 0;
		questionsAnsweredMt1ToMt2 = 0;
		questionsAnsweredMt2ToFinal = 0;
		for (Integer i : preMt1Answers.values()) { questionsAnsweredPreMt1 += i; }
		for (Integer i : mt1ToMt2Answers.values()) { questionsAnsweredMt1ToMt2 += i; }
		for (Integer i : mt2ToFinalAnswers.values()) { questionsAnsweredMt2ToFinal += i; }
		totalQuestionsAnswered = questionsAnsweredPreMt1 + questionsAnsweredMt1ToMt2 + questionsAnsweredMt2ToFinal;
	}
	
}
