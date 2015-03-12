package chara_subsetter;

import java.util.ArrayList;
import java.util.List;

import csv_classes.Course;
import csv_classes.GradeFa13;
import csv_classes.GradeFa14;
import csv_classes.LabQueue;
import csv_classes.LabQueueStaff;
import csv_classes.QueueEntry;
import csv_classes.StaffAssignment;
import csv_classes.User;

public class CharaSubsetter {
	
	List<Course> courses;
	List<QueueEntry> queueEntries;
	List<LabQueueStaff> labQueueStaffs;
	List<LabQueue> labQueues;
	List<StaffAssignment> staffAssignments;
	List<User> users;
	List<GradeFa13> gradesFa13;
	List<GradeFa14> gradesFa14;
	
	public CharaSubsetter() {
		courses = new ArrayList<Course>();
		queueEntries = new ArrayList<QueueEntry>();
		labQueueStaffs = new ArrayList<LabQueueStaff>();
		labQueues = new ArrayList<LabQueue>();
		staffAssignments = new ArrayList<StaffAssignment>();
		users = new ArrayList<User>();
		gradesFa13 = new ArrayList<GradeFa13>();
		gradesFa14 = new ArrayList<GradeFa14>();
	}
	
	public void run() {
		CSVLoader.loadCSVs(courses, queueEntries, labQueueStaffs, labQueues, staffAssignments, users, gradesFa13, gradesFa14);
		
		
	}
	
	
	
}
