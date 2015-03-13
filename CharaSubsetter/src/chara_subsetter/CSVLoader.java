package chara_subsetter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import csv_classes.Course;
import csv_classes.Evaluation;
import csv_classes.GradeFa13;
import csv_classes.GradeFa14;
import csv_classes.LabQueue;
import csv_classes.LabQueueStaff;
import csv_classes.Question;
import csv_classes.QueueEntry;
import csv_classes.StaffAssignment;
import csv_classes.User;

/**
 * Responsible for loading data from the multiple Chara CSVs.
 */
public class CSVLoader {
	
	private static File dataDir = new File("../Data");
	private static File coursesFile = new File(dataDir, "courses.csv");
	private static File questionsFile = new File(dataDir, "questions.csv");
	private static File evaluationsFile = new File(dataDir, "evaluations.csv");
	private static File labQueueStaffsFile = new File(dataDir, "lab_queue_staffs.csv");
	private static File labQueuesFile = new File(dataDir, "lab_queues.csv");
	private static File staffAssignmentsFile = new File(dataDir, "staff_assignments.csv");
	private static File usersFile = new File(dataDir, "users.csv");
	private static File gradesFa13File = new File(dataDir, "gradesFA13.csv");
	private static File gradesFa14File = new File(dataDir, "gradesFA14.csv");
	private static DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
	private static DateFormat questionDateFormatter = new SimpleDateFormat("mm/dd/yyyy HH:mm", Locale.ENGLISH);
	
	public static void loadCSVs(List<Course> courses, List<QueueEntry> queueEntries,
			List<LabQueueStaff> labQueueStaffs, List<LabQueue> labQueues,
			List<StaffAssignment> staffAssignments, List<User> users,
			List<GradeFa13> gradesFa13, List<GradeFa14> gradesFa14) {
		System.out.print("Loading CSV files...");
		loadCourses(courses);
		loadQueueEntries(queueEntries);
		loadLabQueueStaffs(labQueueStaffs);
		loadLabQueues(labQueues);
		loadStaffAssignments(staffAssignments);
		loadUsers(users);
		loadGradesFa13(gradesFa13);
		loadGradesFa14(gradesFa14);
		System.out.println("done.");
	}
	
	private static void loadCourses(List<Course> courses) {
		courses.clear();
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(coursesFile));
			br.readLine(); // Skip top line
			String line = br.readLine();
			while (line != null) {
				String[] tokens = line.split(",");
				courses.add(new Course(
						Integer.valueOf(tokens[0]),
						tokens[1],
						tokens[2],
						Integer.valueOf(tokens[3]),
						(tokens[4].equals("NULL") ? null : dateFormatter.parse(tokens[4])),
						(tokens[5].equals("NULL") ? null : dateFormatter.parse(tokens[5])),
						(tokens[6].equals("NULL") ? null : dateFormatter.parse(tokens[6]))
						));
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) { }
		}
	}
	
	private static void loadQueueEntries(List<QueueEntry> queueEntries) {
		// Load questions file:
		List<Question> questions = new ArrayList<Question>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(questionsFile));
			br.readLine(); // Skip top line
			String line = br.readLine();
			while (line != null) {
				if (line.endsWith(",")) { line += "-"; } // Fix for old Chara records without location
				String[] tokens = line.split(",");
				questions.add(new Question(
						Integer.valueOf(tokens[0]),
						Integer.valueOf(tokens[1]),
						tokens[2],
						(tokens[3].equals("NULL") ? null : questionDateFormatter.parse(tokens[3])),
						(tokens[4].equals("NULL") ? null : questionDateFormatter.parse(tokens[4])),
						Integer.valueOf(tokens[5]),
						(tokens[6].equals("NULL") ? -1 : Integer.valueOf(tokens[6])),
						Integer.valueOf(tokens[7]),
						tokens[8]
						));
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) { }
		}
		
		// Load evaluations file:
		Map<Integer, Evaluation> evaluations = new HashMap<Integer, Evaluation>();
		try {
			br = new BufferedReader(new FileReader(evaluationsFile));
			br.readLine(); // Skip top line
			String line = br.readLine();
			while (line != null) {
				String[] tokens = line.split(",");
				evaluations.put(Integer.valueOf(tokens[5]), new Evaluation(
						Integer.valueOf(tokens[0]),
						tokens[1],
						Integer.valueOf(tokens[2]),
						(tokens[3].equals("NULL") ? null : dateFormatter.parse(tokens[3])),
						(tokens[4].equals("NULL") ? null : dateFormatter.parse(tokens[4])),
						Integer.valueOf(tokens[5])
						));
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) { }
		}
		
		// Create QueueEntries:
		queueEntries.clear();
		for (Question question : questions) {
			Evaluation evaluation = evaluations.get(question.getId());
			queueEntries.add(new QueueEntry(
					question.getId(),
					question.getTopic(),
					question.getCreatedAt(),
					question.getDeletedAt(),
					question.getAskerId(),
					question.getAnswererId(),
					question.getLabQueueId(),
					question.getLocation(),
					(evaluation == null ? null : evaluation.getCreatedAt()),
					(evaluation == null ? null : evaluation.getDeletedAt())
					));
		}
	}
	
	private static void loadLabQueueStaffs(List<LabQueueStaff> labQueueStaffs) {
		labQueueStaffs.clear();
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(labQueueStaffsFile));
			br.readLine(); // Skip top line
			String line = br.readLine();
			while (line != null) {
				String[] tokens = line.split(",");
				labQueueStaffs.add(new LabQueueStaff(
						Integer.valueOf(tokens[0]),
						(tokens[1].equals("NULL") ? null : dateFormatter.parse(tokens[1])),
						(tokens[2].equals("NULL") ? null : dateFormatter.parse(tokens[2])),
						(tokens[3].equals("NULL") ? null : dateFormatter.parse(tokens[3])),
						Integer.valueOf(tokens[4]),
						Integer.valueOf(tokens[5])
						));
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) { }
		}
	}
	
	private static void loadLabQueues(List<LabQueue> labQueues) {
		labQueues.clear();
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(labQueuesFile));
			br.readLine(); // Skip top line
			String line = br.readLine();
			while (line != null) {
				String[] tokens = line.split(",");
				labQueues.add(new LabQueue(
						Integer.valueOf(tokens[0]),
						tokens[1],
						(tokens[2].equals("NULL") ? null : dateFormatter.parse(tokens[2])),
						(tokens[3].equals("NULL") ? null : dateFormatter.parse(tokens[3])),
						Integer.valueOf(tokens[4])
						));
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) { }
		}
	}
	
	private static void loadStaffAssignments(List<StaffAssignment> staffAssignments) {
		staffAssignments.clear();
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(staffAssignmentsFile));
			br.readLine(); // Skip top line
			String line = br.readLine();
			while (line != null) {
				String[] tokens = line.split(",");
				staffAssignments.add(new StaffAssignment(
						Integer.valueOf(tokens[0]),
						Integer.valueOf(tokens[1])
						));
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) { }
		}
	}
	
	private static void loadUsers(List<User> users) {
		users.clear();
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(usersFile));
			br.readLine(); // Skip top line
			String line = br.readLine();
			while (line != null) {
				String[] tokens = line.split(",");
				users.add(new User(
						Integer.valueOf(tokens[0]),
						tokens[1]
						));
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) { }
		}
	}
	
	private static void loadGradesFa13(List<GradeFa13> gradesFa13) {
		gradesFa13.clear();
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(gradesFa13File));
			br.readLine(); // Skip top line
			String line = br.readLine();
			while (line != null) {
				String[] tokens = line.split(",");
				gradesFa13.add(new GradeFa13(
						tokens[0],
						(tokens[1].equals("-") ? 0 : Double.valueOf(tokens[1])),
						(tokens[2].equals("-") ? 0 : Double.valueOf(tokens[2])),
						(tokens[3].equals("-") ? 0 : Double.valueOf(tokens[3])),
						(tokens[4].equals("-") ? 0 : Double.valueOf(tokens[4])),
						(tokens[5].equals("-") ? 0 : Double.valueOf(tokens[5])),
						(tokens[6].equals("-") ? 0 : Double.valueOf(tokens[6])),
						(tokens[7].equals("-") ? 0 : Double.valueOf(tokens[7])),
						(tokens[8].equals("-") ? 0 : Double.valueOf(tokens[8])),
						(tokens[9].equals("-") ? 0 : Double.valueOf(tokens[9])),
						(tokens[10].equals("-") ? 0 : Double.valueOf(tokens[10])),
						(tokens[11].equals("-") ? 0 : Double.valueOf(tokens[11])),
						(tokens[12].equals("-") ? 0 : Double.valueOf(tokens[12])),
						(tokens[13].equals("-") ? 0 : Double.valueOf(tokens[13])),
						(tokens[14].equals("-") ? 0 : Double.valueOf(tokens[14])),
						(tokens[15].equals("-") ? 0 : Double.valueOf(tokens[15])),
						(tokens[16].equals("-") ? 0 : Double.valueOf(tokens[16])),
						(tokens[17].equals("-") ? 0 : Double.valueOf(tokens[17])),
						(tokens[18].equals("-") ? 0 : Double.valueOf(tokens[18])),
						(tokens[19].equals("-") ? 0 : Double.valueOf(tokens[19])),
						(tokens[20].equals("-") ? 0 : Double.valueOf(tokens[20])),
						(tokens[21].equals("-") ? 0 : Double.valueOf(tokens[21])),
						(tokens[22].equals("-") ? 0 : Double.valueOf(tokens[22])),
						(tokens[23].equals("-") ? 0 : Double.valueOf(tokens[23])),
						(tokens[24].equals("-") ? 0 : Double.valueOf(tokens[24])),
						(tokens[25].equals("-") ? 0 : Double.valueOf(tokens[25])),
						(tokens[26].equals("-") ? 0 : Double.valueOf(tokens[26]))
						));
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) { }
		}
	}
	
	private static void loadGradesFa14(List<GradeFa14> gradesFa14) {
		gradesFa14.clear();
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(gradesFa14File));
			br.readLine(); // Skip top line
			String line = br.readLine();
			while (line != null) {
				String[] tokens = line.split(",");
				gradesFa14.add(new GradeFa14(
						tokens[0],
						(tokens[1].equals("-") ? 0 : Double.valueOf(tokens[1])),
						(tokens[2].equals("-") ? 0 : Double.valueOf(tokens[2])),
						(tokens[3].equals("-") ? 0 : Double.valueOf(tokens[3])),
						(tokens[4].equals("-") ? 0 : Double.valueOf(tokens[4])),
						(tokens[5].equals("-") ? 0 : Double.valueOf(tokens[5])),
						(tokens[6].equals("-") ? 0 : Double.valueOf(tokens[6])),
						(tokens[7].equals("-") ? 0 : Double.valueOf(tokens[7])),
						(tokens[8].equals("-") ? 0 : Double.valueOf(tokens[8])),
						(tokens[9].equals("-") ? 0 : Double.valueOf(tokens[9])),
						(tokens[10].equals("-") ? 0 : Double.valueOf(tokens[10])),
						(tokens[11].equals("-") ? 0 : Double.valueOf(tokens[11])),
						(tokens[12].equals("-") ? 0 : Double.valueOf(tokens[12])),
						(tokens[13].equals("-") ? 0 : Double.valueOf(tokens[13])),
						(tokens[14].equals("-") ? 0 : Double.valueOf(tokens[14])),
						(tokens[15].equals("-") ? 0 : Double.valueOf(tokens[15])),
						(tokens[16].equals("-") ? 0 : Double.valueOf(tokens[16])),
						(tokens[17].equals("-") ? 0 : Double.valueOf(tokens[17])),
						(tokens[18].equals("-") ? 0 : Double.valueOf(tokens[18])),
						(tokens[19].equals("-") ? 0 : Double.valueOf(tokens[19])),
						(tokens[20].equals("-") ? 0 : Double.valueOf(tokens[20])),
						(tokens[21].equals("-") ? 0 : Double.valueOf(tokens[21])),
						(tokens[22].equals("-") ? 0 : Double.valueOf(tokens[22])),
						(tokens[23].equals("-") ? 0 : Double.valueOf(tokens[23])),
						(tokens[24].equals("-") ? 0 : Double.valueOf(tokens[24])),
						(tokens[25].equals("-") ? 0 : Double.valueOf(tokens[25])),
						(tokens[26].equals("-") ? 0 : Double.valueOf(tokens[26]))
						));
				line = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) { }
		}
	}
	
}
