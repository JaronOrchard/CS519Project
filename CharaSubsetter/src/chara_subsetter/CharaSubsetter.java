package chara_subsetter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import csv_classes.Course;

public class CharaSubsetter {
	
	List<Course> courses;
	
	public void run() {
		System.out.print("Loading...");
		
		File dataDir = new File("../Data");
		loadCourses(new File(dataDir, "courses.csv"));
		
		System.out.println("done.");
	}
	
	private void loadCourses(File coursesFile) {
		courses = new ArrayList<Course>();
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		
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
	
}
