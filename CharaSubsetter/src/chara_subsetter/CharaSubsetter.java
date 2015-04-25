package chara_subsetter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mega_classes.MegaStudent;
import csv_classes.Course;
import csv_classes.GradeFa13;
import csv_classes.GradeFa14;
import csv_classes.LabQueue;
import csv_classes.LabQueueStaff;
import csv_classes.QueueEntry;
import csv_classes.StaffAssignment;
import csv_classes.User;

public class CharaSubsetter {
	
	final long CS225_FA13_MT1_TIME = 1380654000000L; // Unix timestamp of CS 225 Fall 2013's midterm 1 (10/1/2013)
	final long CS225_FA13_MT2_TIME = 1383678000000L; // Unix timestamp of CS 225 Fall 2013's midterm 2 (11/5/2014)
	
	final long CS225_FA14_MT1_TIME = 1412103600000L; // Unix timestamp of CS 225 Fall 2014's midterm 1 (9/30/2014)
	final long CS225_FA14_MT2_TIME = 1415127600000L; // Unix timestamp of CS 225 Fall 2014's midterm 2 (11/4/2014)
	
	List<Course> courses;
	List<QueueEntry> queueEntries;
	List<LabQueueStaff> labQueueStaffs;
	List<LabQueue> labQueues;
	List<StaffAssignment> staffAssignments;
	List<User> users;
	List<GradeFa13> gradesFa13;
	List<GradeFa14> gradesFa14;
	Map<Integer, String> userIdToNetIdMap;
	Map<String, Integer> userNetIdToIdMap;
	
	public CharaSubsetter() {
		courses = new ArrayList<Course>();
		queueEntries = new ArrayList<QueueEntry>();
		labQueueStaffs = new ArrayList<LabQueueStaff>();
		labQueues = new ArrayList<LabQueue>();
		staffAssignments = new ArrayList<StaffAssignment>();
		users = new ArrayList<User>();
		gradesFa13 = new ArrayList<GradeFa13>();
		gradesFa14 = new ArrayList<GradeFa14>();
		userIdToNetIdMap = new HashMap<Integer, String>();
		userNetIdToIdMap = new HashMap<String, Integer>();
		// Load/populate data:
		CSVLoader.loadCSVs(courses, queueEntries, labQueueStaffs, labQueues, staffAssignments, users, gradesFa13, gradesFa14);
		for (User user : users) {
			userIdToNetIdMap.put(user.getId(), user.getNetId());
			userNetIdToIdMap.put(user.getNetId(), user.getId());
		}
	}
	
	/**
	 * @return A mapping of student IDs to their populated MegaStudent objects.
	 */
	private Collection<MegaStudent> getMegaStudentsFa13() {
		Map<Integer, MegaStudent> megaStudentsFa13Map = new HashMap<Integer, MegaStudent>();
		int idForStudentsWhoNeverUsedChara = 100000; // Increments over time
		// Populate map with one MegaStudent object for each student...
		for (GradeFa13 grade : gradesFa13) {
			int studentId = (userNetIdToIdMap.get(grade.getNetId()) == null ? idForStudentsWhoNeverUsedChara++ : userNetIdToIdMap.get(grade.getNetId()));
			MegaStudent megaStudent = new MegaStudent(studentId, grade.getNetId(), grade.getMt1(), grade.getMt2(), grade.getFinalExam(), grade.getTotal());
			megaStudentsFa13Map.put(studentId, megaStudent);
		}
		// For each answered QueueEntry, add it to the appropriate MegaStudent object
		List<QueueEntry> queueEntries = getCS225Fa13QueueEntries();
		for (QueueEntry queueEntry : queueEntries) {
			if (queueEntry.wasAnswered() && megaStudentsFa13Map.containsKey(queueEntry.getAskerId())) {
				if (queueEntry.getEvaluationCreatedAt().getTime() < CS225_FA13_MT1_TIME) {
					megaStudentsFa13Map.get(queueEntry.getAskerId()).addPreMt1Answer(queueEntry.getAnswererId());
				} else if (queueEntry.getEvaluationCreatedAt().getTime() < CS225_FA13_MT2_TIME) {
					megaStudentsFa13Map.get(queueEntry.getAskerId()).addMt1ToMt2Answer(queueEntry.getAnswererId());
				} else {
					megaStudentsFa13Map.get(queueEntry.getAskerId()).addMt2ToFinalAnswer(queueEntry.getAnswererId());
				}
			}
		}
		// For each MegaStudent, finalize its answered question count
		for (MegaStudent megaStudent : megaStudentsFa13Map.values()) { megaStudent.finalizeTotalQuestionsAnswered(); }
		// Return collection of MegaStudent objects
		return megaStudentsFa13Map.values();
	}
	
	/**
	 * @return A mapping of student IDs to their populated MegaStudent objects.
	 */
	private Collection<MegaStudent> getMegaStudentsFa14() {
		Map<Integer, MegaStudent> megaStudentsFa14Map = new HashMap<Integer, MegaStudent>();
		int idForStudentsWhoNeverUsedChara = 100000; // Increments over time
		// Populate map with one MegaStudent object for each student...
		for (GradeFa14 grade : gradesFa14) {
			int studentId = (userNetIdToIdMap.get(grade.getNetId()) == null ? idForStudentsWhoNeverUsedChara++ : userNetIdToIdMap.get(grade.getNetId()));
			MegaStudent megaStudent = new MegaStudent(studentId, grade.getNetId(), grade.getMt1(), grade.getMt2(), grade.getFinalExam(), grade.getTotal());
			megaStudentsFa14Map.put(studentId, megaStudent);
		}
		// For each answered QueueEntry, add it to the appropriate MegaStudent object
		List<QueueEntry> queueEntries = getCS225Fa14QueueEntries();
		for (QueueEntry queueEntry : queueEntries) {
			if (queueEntry.wasAnswered() && megaStudentsFa14Map.containsKey(queueEntry.getAskerId())) {
				if (queueEntry.getEvaluationCreatedAt().getTime() < CS225_FA14_MT1_TIME) {
					megaStudentsFa14Map.get(queueEntry.getAskerId()).addPreMt1Answer(queueEntry.getAnswererId());
				} else if (queueEntry.getEvaluationCreatedAt().getTime() < CS225_FA14_MT2_TIME) {
					megaStudentsFa14Map.get(queueEntry.getAskerId()).addMt1ToMt2Answer(queueEntry.getAnswererId());
				} else {
					megaStudentsFa14Map.get(queueEntry.getAskerId()).addMt2ToFinalAnswer(queueEntry.getAnswererId());
				}
			}
		}
		// For each MegaStudent, finalize its answered question count
		for (MegaStudent megaStudent : megaStudentsFa14Map.values()) { megaStudent.finalizeTotalQuestionsAnswered(); }
		// Return collection of MegaStudent objects
		return megaStudentsFa14Map.values();
	}
	
	private void printMegaStudentsCSV(Collection<MegaStudent> megaStudents) {
		System.out.println("id,grade_mt1,grade_mt2,grade_final,grade_overall,questions_pre_mt1,questions_mt1_to_mt2,questions_mt2_to_final,questions_total");
		for (MegaStudent megaStudent : megaStudents) {
			System.out.println(megaStudent.getId() + "," + megaStudent.getMt1Grade() + "," + megaStudent.getMt2Grade() + "," +
					megaStudent.getFinalExamGrade() + "," + megaStudent.getOverallGrade() + "," + megaStudent.getQuestionsAnsweredPreMt1() + "," +
					megaStudent.getQuestionsAnsweredMt1ToMt2() + "," + megaStudent.getQuestionsAnsweredMt2ToFinal() + "," +
					megaStudent.getTotalQuestionsAnswered());
		}
	}
	
	private void printApplicableQueueEntriesFa13() {
		Set<Integer> studentIds = new HashSet<Integer>();
		for (GradeFa13 grade : gradesFa13) {
			Integer studentId = (userNetIdToIdMap.get(grade.getNetId()));
			if (studentId != null) {
				studentIds.add(studentId);
			}
		}
		List<QueueEntry> queueEntries = getCS225Fa13QueueEntries();
		System.out.println("student_id,ta_id,question_asked_time,question_answered_time");
		for (QueueEntry queueEntry : queueEntries) {
			if (queueEntry.getEvaluationCreatedAt() != null && queueEntry.getAnswererId() != -1 &&
					queueEntry.getQuestionCreatedAt() != null && studentIds.contains(queueEntry.getAskerId())) {
				System.out.println(queueEntry.getAskerId() + "," + queueEntry.getAnswererId() + "," +
						queueEntry.getQuestionCreatedAt().getTime() + "," + queueEntry.getEvaluationCreatedAt().getTime());
			}
		}
	}
	
	private void printApplicableQueueEntriesFa14() {
		Set<Integer> studentIds = new HashSet<Integer>();
		for (GradeFa14 grade : gradesFa14) {
			Integer studentId = (userNetIdToIdMap.get(grade.getNetId()));
			if (studentId != null) {
				studentIds.add(studentId);
			}
		}
		List<QueueEntry> queueEntries = getCS225Fa14QueueEntries();
		System.out.println("student_id,ta_id,question_asked_time,question_answered_time");
		for (QueueEntry queueEntry : queueEntries) {
			if (queueEntry.getEvaluationCreatedAt() != null && queueEntry.getAnswererId() != -1 &&
					queueEntry.getQuestionCreatedAt() != null && studentIds.contains(queueEntry.getAskerId())) {
				System.out.println(queueEntry.getAskerId() + "," + queueEntry.getAnswererId() + "," +
						queueEntry.getQuestionCreatedAt().getTime() + "," + queueEntry.getEvaluationCreatedAt().getTime());
			}
		}
	}
	
	public void run() {
		printApplicableQueueEntriesFa13();
		//printMegaStudentsCSV(getMegaStudentsFa14());
		
		// Prints a CSV of number of questions a student had answered to his/her overall course score:
		/*dropNonCS225Entries();
		Map<String, Double> studentsFa13 = Queries.getCS225Fa13CompletedStudents(gradesFa13);
		Map<String, Double> studentsFa14 = Queries.getCS225Fa14CompletedStudents(gradesFa14);
		Set<String> studentNetIds = new HashSet<String>();
		for (String student : studentsFa13.keySet()) { studentNetIds.add(student); }
		for (String student : studentsFa14.keySet()) { studentNetIds.add(student); }
		Map<String, Integer> answeredQuestions = Queries.getNumberOfAnsweredQuestions(studentNetIds, queueEntries, users);
		for (String student : studentsFa13.keySet()) {
			System.out.println(student + "," + answeredQuestions.get(student) + "," + studentsFa13.get(student));  
		}
		for (String student : studentsFa14.keySet()) {
			System.out.println(student + "," + answeredQuestions.get(student) + "," + studentsFa14.get(student));  
		}*/
		
		// Early test
		/*
		System.out.println("# of QueueEntries: " + queueEntries.size());
		dropNonCS225Entries();
		System.out.println("Removed non-CS 225 entries.");
		Queries.reportMostActiveTAs(queueEntries);
		System.out.println("# of QueueEntries: " + queueEntries.size());
		*/
	}
	
	/**
	 * Removes any QueueEntries that didn't take place in CS 225 Fall 2013 or Fall 2014.
	 */
	public void dropNonCS225Entries() {
		Set<Integer> validQueueIds = new HashSet<Integer>();
		for (LabQueue labQueue : labQueues) {
			if (labQueue.getCourseId() == 4 || labQueue.getCourseId() == 11) {
				validQueueIds.add(labQueue.getId());
			}
		}
		Iterator<QueueEntry> queueEntryIter = queueEntries.iterator();
		while (queueEntryIter.hasNext()) {
			QueueEntry queueEntry = queueEntryIter.next();
			if (!validQueueIds.contains(queueEntry.getLabQueueId())) {
				queueEntryIter.remove();
			}
		}
	}
	
	/**
	 * @return The subset of {@link QueueEntries} that only pertains to CS 225 Fall 2013.
	 */
	public List<QueueEntry> getCS225Fa13QueueEntries() {
		Set<Integer> validQueueIds = new HashSet<Integer>();
		for (LabQueue labQueue : labQueues) {
			if (labQueue.getCourseId() == 4) {
				validQueueIds.add(labQueue.getId());
			}
		}
		List<QueueEntry> queueEntriesFa13 = new ArrayList<QueueEntry>();
		for (QueueEntry queueEntry : queueEntries) {
			if (validQueueIds.contains(queueEntry.getLabQueueId())) {
				queueEntriesFa13.add(queueEntry);
			}
		}
		return queueEntriesFa13;
	}
	
	/**
	 * @return The subset of {@link QueueEntries} that only pertains to CS 225 Fall 2014.
	 */
	public List<QueueEntry> getCS225Fa14QueueEntries() {
		Set<Integer> validQueueIds = new HashSet<Integer>();
		for (LabQueue labQueue : labQueues) {
			if (labQueue.getCourseId() == 11) {
				validQueueIds.add(labQueue.getId());
			}
		}
		List<QueueEntry> queueEntriesFa14 = new ArrayList<QueueEntry>();
		for (QueueEntry queueEntry : queueEntries) {
			if (validQueueIds.contains(queueEntry.getLabQueueId())) {
				queueEntriesFa14.add(queueEntry);
			}
		}
		return queueEntriesFa14;
	}
	
}
