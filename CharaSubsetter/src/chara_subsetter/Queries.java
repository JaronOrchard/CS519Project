package chara_subsetter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import csv_classes.GradeFa13;
import csv_classes.GradeFa14;
import csv_classes.QueueEntry;
import csv_classes.User;

public class Queries {
	
	/**
	 * Lists the top 5 TAs who have answered the most queue questions.
	 */
	public static void reportMostActiveTAs(List<QueueEntry> queueEntries) {
		Map<Integer, Integer> mostActiveTAs = new HashMap<Integer, Integer>();
		for (QueueEntry queueEntry : queueEntries) {
			int answererId = queueEntry.getAnswererId();
			if (answererId != -1) {
				if (!mostActiveTAs.containsKey(answererId)) {
					mostActiveTAs.put(answererId, 1);
				} else {
					mostActiveTAs.put(answererId, mostActiveTAs.get(answererId) + 1);
				}
			}
		}
		// Report top 5:
		System.out.println("Top 5 TA answerers:");
		System.out.println("-------------------");
		for (int i = 0; i < 5; i++) {
			int answererId = -1;
			int mostAnswers = -1;
			for (Entry<Integer, Integer> entry : mostActiveTAs.entrySet()) {
				if (entry.getValue() > mostAnswers) {
					answererId = entry.getKey();
					mostAnswers = entry.getValue();
				}
			}
			System.out.println("TA #" + answererId + ": " + mostAnswers + " questions answered");
			mostActiveTAs.remove(answererId);
		}
		System.out.println("");
	}
	
	/**
	 * Returns a map of NetIDs and final grades of all students who completed CS 225 in Fall 2013.
	 */
	public static Map<String, Double> getCS225Fa13Students(List<GradeFa13> grades) {
		Map<String, Double> students = new HashMap<String, Double>();
		for (GradeFa13 grade : grades) {
			if (grade.getTotal() != 0) {
				students.put(grade.getNetId(), grade.getTotal());
			}
		}
		return students;
	}

	/**
	 * Returns a map of NetIDs and final grades of all students who completed CS 225 in Fall 2014.
	 */
	public static Map<String, Double> getCS225Fa14Students(List<GradeFa14> grades) {
		Map<String, Double> students = new HashMap<String, Double>();
		for (GradeFa14 grade : grades) {
			if (grade.getTotal() != 0) {
				students.put(grade.getNetId(), grade.getTotal());
			}
		}
		return students;
	}
	
	/**
	 * Given a set of student netIds, returns the number of times they
	 * had a question answered on the Chara queue.
	 */
	public static Map<String, Integer> getNumberOfAnsweredQuestions(Set<String> studentNetIds, List<QueueEntry> queueEntries, List<User> users) {
		Map<String, Integer> count = new HashMap<String, Integer>();
		for (String studentNetId : studentNetIds) {
			int userId = -1;
			for (User user : users) {
				if (user.getNetId().equals(studentNetId)) {
					userId = user.getId();
					break;
				}
			}
			if (userId == -1) {
				// Could not find user id--this person never used the queue.
				count.put(studentNetId, 0);
				continue;
			}
			int questionsAnswered = 0;
			for (QueueEntry queueEntry : queueEntries) {
				if (queueEntry.getAskerId() == userId && queueEntry.getAnswererId() != -1) {
					questionsAnswered++;
				}
			}
			count.put(studentNetId, questionsAnswered);
		}
		return count;
	}
	
}
