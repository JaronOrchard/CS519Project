package chara_subsetter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import csv_classes.QueueEntry;

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
	
}
