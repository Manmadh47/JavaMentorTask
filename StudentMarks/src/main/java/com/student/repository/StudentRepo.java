package com.student.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.student.logging.Loggings;
import com.student.model.Student;
import com.student.model.SubjectMarks;

public class StudentRepo implements IStudentRepo {

	public List<Student> studentsList = new ArrayList<Student>();

	public Loggings logs=new Loggings();

	public void readDataFromFile() throws IOException {
		InputStream is = Files.newInputStream(Paths.get("../StudentMarks/src/main/java/files/marks.txt"));

		final BufferedReader buffReader = new BufferedReader(new InputStreamReader(is));
		
		String line;
		int recCount=0;
		
		try {
			while ((line = buffReader.readLine()) != null) {
				if (!(line.isBlank())) {

					String[] values = line.split("\\|");
					recCount++;
					boolean status=logs.logErrors(values,recCount);	//logs error if field is missing
					
					if(status==false) {			//skipping field missing record.
						
					String firstName = values[0];
					String lastName = values[1];
					String branch = values[2];
					int maths = Integer.parseInt(values[3]);
					int biology = Integer.parseInt(values[4]);
					int computers = Integer.parseInt(values[5]);
					String nationality = values[6];

					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
					LocalDate dob = LocalDate.parse(values[7], formatter);

					SubjectMarks marks = new SubjectMarks(maths, biology, computers);
					Student stObj = new Student(firstName, lastName, marks, branch, nationality, dob);
					studentsList.add(stObj);
					}
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		finally {
			buffReader.close();
		}
	}

	public List<Student> getAllStudentDetails() {
		try {
			readDataFromFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return studentsList;
	}

	public void calculateGrade(List<Student> studentsList) {
		studentsList.stream().forEach(st -> {
			if (st.getSubject().getAverageMarks() >= 91 && st.getSubject().getAverageMarks() <= 100) {
				st.setGrade("A+");
			} else if (st.getSubject().getAverageMarks() >= 81 && st.getSubject().getAverageMarks() <= 90) {
				st.setGrade("A");
			} else if (st.getSubject().getAverageMarks() >= 71 && st.getSubject().getAverageMarks() <= 80) {
				st.setGrade("B+");
			} else if (st.getSubject().getAverageMarks() >= 61 && st.getSubject().getAverageMarks() <= 70) {
				st.setGrade("C+");
			} else {
				st.setGrade("D");
			}
		});
	}

}
