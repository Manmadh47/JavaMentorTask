package com.student.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.student.model.Student;
import com.student.repository.IStudentRepo;
import com.student.repository.StudentRepo;

public class StudentReportService {

	public IStudentRepo studentRepo=new StudentRepo();
	public List<Student> studentsList = studentRepo.getAllStudentDetails();
	

	public Map<String, Object> getTopScorer(List<Student> studentsList) {

		final Map<String, Object> topScorers = studentsList.stream()
				.collect(Collectors.groupingBy(Student::getBranch,
						Collectors.collectingAndThen(
								Collectors.maxBy(Comparator.comparing(s -> s.getSubject().getAverageMarks())),
								s -> s.get().getFirstName())));
		return topScorers;
	}

	public long getNumberOfIndianStudents(List<Student> studentsList) {
		final long indiansCount = studentsList.stream().filter(st -> st.getNationality().equalsIgnoreCase("Indian")).count();
		return indiansCount;
	}

	public LinkedHashMap<String, List<Map<String, Integer>>> getStudentAggregateMarks(List<Student> studentsList) {

		final LinkedHashMap<String, List<Map<String, Integer>>> avgMarks = studentsList.stream()
				.sorted(Comparator.comparing(st -> st.getSubject().getAverageMarks(), Comparator.reverseOrder()))
				.collect(Collectors.groupingBy(
		        Student::getBranch, LinkedHashMap::new, Collectors.collectingAndThen(Collectors.toList(),
		                l -> l.stream()
		                        .map(student -> Collections.singletonMap(student.getFirstName(), student.getSubject().getAverageMarks()))
		                        .collect(Collectors.toList()))));
		
		return avgMarks;
	}

	public Map<String, List<String>> getStudentsByDOB(List<Student> studentsList) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
		LocalDate dob = LocalDate.parse("01/01/1990", formatter);

		final Predicate<Student> predByDOB = s -> s.getDob().isBefore(dob);
		Map<String, List<String>> stByDob = studentsList.stream().filter(predByDOB).collect(Collectors
				.groupingBy(Student::getBranch, Collectors.mapping(Student::getFirstName, Collectors.toList())));

		return stByDob;
	}

	public Map<String, Map<String, String>> getShortHandNotations(List<Student> studentsList) {

		studentsList.stream().peek(st -> {
			String name1 = st.getFirstName().substring(0, 1) + st.getLastName().substring(0, 1);
			final String name2 = st.getFirstName().substring(1, 2) + st.getLastName().substring(1, 2);
			if (studentsList.stream().anyMatch(s -> s.getShortName().equals(name1))) {
				st.setShortName(name2);
			} else {
				st.setShortName(name1);
			}
		}).collect(Collectors.toList());

		final Map<String, Map<String, String>> getByshortNames = studentsList.stream()
		        .collect(Collectors.groupingBy(Student::getShortName, 
		                 Collectors.toMap(Student::getFirstName, Student::getLastName)));
		
		return getByshortNames;
	}

	public Map<String, Map<String, Long>> getNumberOfStudentsInEachGrade(List<Student> studentsList) {
		studentRepo.calculateGrade(studentsList);
		
		final Map<String, Map<String, Long>> stByGrades = studentsList.stream()
				.collect(Collectors.groupingBy(Student::getBranch,
						Collectors.groupingBy(Student::getGrade, Collectors.counting())));

		return stByGrades;
	}

	public Map<String, List<String>> getForeignStudents(List<Student> studentsList) {

		final Map<String, List<String>> foreigners = studentsList.stream()
				.filter(s -> s.getNationality().equalsIgnoreCase("Foreign")).collect(Collectors.groupingBy(
						Student::getBranch, Collectors.mapping(Student::getFirstName, Collectors.toList())));

		return foreigners;
	}
}
