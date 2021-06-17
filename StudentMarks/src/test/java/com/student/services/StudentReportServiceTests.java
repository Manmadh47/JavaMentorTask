package com.student.services;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.student.model.Student;
import com.student.model.SubjectMarks;

public class StudentReportServiceTests {

	private static final String MSG_01 = "should return top scorer in each branch";
	private static final String MSG_02 = "should return total count of Indian students";
	private static final String MSG_03 = "should return sorted aggregate marks of each student each departments";
	private static final String MSG_04 = "should return names of the students in each class who was born before 01/01/1990";
	private static final String MSG_05 = "should return short hand notation for each student";
	private static final String MSG_06 = "should return number Of students in each grade";
	private static final String MSG_07 = "should return foriegn students in each department";
	
	private StudentReportService studentReportService;
	private List<Student> studentList;

	@BeforeEach
	public void setUp() {
		studentReportService = new StudentReportService();
		studentList = List.of(
				new Student("Arun", "Kumar", new SubjectMarks(89, 87, 66), "CSE", "INDIAN", LocalDate.of(1990, 12, 22)),
				new Student("TOM", "JOHN", new SubjectMarks(77, 99, 77), "ECE", "FOREIGN", LocalDate.of(1989, 12, 15)),
				new Student("JOHN", "KIM", new SubjectMarks(99, 78, 65), "ME", "INDIAN", LocalDate.of(1990, 07, 30)),
				new Student("Vivek", "Raj", new SubjectMarks(65, 88, 85), "ME", "FOREIGN", LocalDate.of(1998, 06, 15)),
				new Student("Meghan", "Andy", new SubjectMarks(83, 77, 95), "EEE", "FOREIGN",LocalDate.of(1991, 12, 20)));
	}

	@Test
	public void givenStudentListThenReturnTopScorerInEachBranch() {
		Map<String, Object> scorer = new HashMap<>();
		scorer.put("EEE", "Meghan");
		scorer.put("CSE", "Arun");
		scorer.put("ECE", "TOM");
		scorer.put("ME", "JOHN");
		assertThat(MSG_01, studentReportService.getTopScorer(studentList), is(scorer));
	}

	@Test
	public void getTotalCountOfIndianStudentsInAllDepartments() {
		assertThat(MSG_02, studentReportService.getNumberOfIndianStudents(studentList), is(2L));
	}

	@Test
	public void getSortedAggregateMarksOfEachStudentEachDepartments() {
		Map<String, List<Map<String, Integer>>> outerMap = new LinkedHashMap<>();
		List<Map<String, Integer>> list=new ArrayList<Map<String, Integer>>();
		Map<String, Integer> innerMap = new LinkedHashMap<>();
		
		innerMap.put("Meghan", 85);
		outerMap.put("EEE", Arrays.asList(innerMap));
		
		innerMap = new LinkedHashMap<>();
		innerMap.put("TOM", 84);
		outerMap.put("ECE", Arrays.asList(innerMap));
		
		innerMap = new LinkedHashMap<>();
		innerMap.put("Arun", 80);
		outerMap.put("CSE", Arrays.asList(innerMap));
		
		innerMap = new LinkedHashMap<>();
		innerMap.put("JOHN", 80);
		Map<String, Integer> innerMap1 = new LinkedHashMap<>();
		innerMap1.put("Vivek", 79);
		outerMap.put("ME", Arrays.asList(innerMap,innerMap1));
		
		assertThat(MSG_03,studentReportService.getStudentAggregateMarks(studentList),is(outerMap));	
	}

	@Test
	public void getStudentsBornBeforeGivenDate() {
		Map<String, List<String>> outerMap = new LinkedHashMap<>();
		outerMap.put("ECE", Arrays.asList("TOM"));

		assertThat(MSG_04, studentReportService.getStudentsByDOB(studentList), is(outerMap));
	}

	@Test
	public void getShorthandNotationForEachStudent() {
		Map<String, Map<String, String>> outerMap = new LinkedHashMap<>();
		Map<String, String> innerMap = new LinkedHashMap<>();

		innerMap = new LinkedHashMap<>();
		innerMap.put("JOHN", "KIM");
		outerMap.put("JK", innerMap);

		innerMap = new LinkedHashMap<>();
		innerMap.put("Meghan", "Andy");
		outerMap.put("MA", innerMap);

		innerMap = new LinkedHashMap<>();
		innerMap.put("TOM", "JOHN");
		outerMap.put("TJ", innerMap);

		innerMap = new LinkedHashMap<>();
		innerMap.put("Arun", "Kumar");
		outerMap.put("AK", innerMap);

		innerMap = new LinkedHashMap<>();
		innerMap.put("Vivek", "Raj");
		outerMap.put("VR", innerMap);

		assertThat(MSG_05, studentReportService.getShortHandNotations(studentList), is(outerMap));
	}

	@Test
	public void getCountOfStudentsInEachGradeInEveryBranch() {
		Map<String, Map<String, Long>> outerMap = new LinkedHashMap<>();
		Map<String, Long> innerMap = new LinkedHashMap<>();

		innerMap.put("A",(long) 1);
		outerMap.put("EEE", innerMap);
		
		innerMap = new LinkedHashMap<>();
		innerMap.put("B+",(long) 1);
		outerMap.put("CSE", innerMap);
		
		innerMap = new LinkedHashMap<>();
		innerMap.put("A",(long) 1);
		outerMap.put("ECE", innerMap);
		
		innerMap = new LinkedHashMap<>();
		innerMap.put("B+",(long) 2);
		outerMap.put("ME", innerMap);
		assertThat(MSG_06, studentReportService.getNumberOfStudentsInEachGrade(studentList), is(outerMap));
	}
	
	@Test
	public void getForiegnStudentsInEachDepartment() {
		Map<String, List<String>> outerMap = new LinkedHashMap<>();
		outerMap.put("EEE", Arrays.asList("Meghan"));
		outerMap.put("ECE", Arrays.asList("TOM"));
		outerMap.put("ME", Arrays.asList("Vivek"));
		
		assertThat(MSG_07, studentReportService.getForeignStudents(studentList), is(outerMap));
	}
}
