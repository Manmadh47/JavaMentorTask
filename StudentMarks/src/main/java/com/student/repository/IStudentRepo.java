package com.student.repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.student.model.Student;

public interface IStudentRepo {
	public void readDataFromFile() throws FileNotFoundException, IOException;

	public List<Student> getAllStudentDetails();

	public void calculateGrade(List<Student> stList);
}
