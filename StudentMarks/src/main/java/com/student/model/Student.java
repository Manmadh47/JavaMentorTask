package com.student.model;

import java.time.LocalDate;

public class Student {
	private String firstName,lastName,branch,nationality,grade,shName;
	private SubjectMarks subject;
	private LocalDate dob;
	
	public Student(String firstName, String lastName,SubjectMarks subject, String branch, String nationality,
			 LocalDate dob) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.branch = branch;
		this.nationality = nationality;
		this.grade = "";
		this.subject = subject;
		this.dob = dob;
		this.shName="";
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getGrade() {
		return grade;
	}

	public String setGrade(String grade) {
		return this.grade = grade;
	}

	public SubjectMarks getSubject() {
		return subject;
	}

	public void setSubject(SubjectMarks subject) {
		this.subject = subject;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	
	public String getShortName() {
		return this.shName;
	}
	
	public void setShortName(String shName) {
		this.shName = shName;
	}
	
	@Override
	public String toString() {
		return "\nStudent [firstName=" + firstName + ", lastName=" + lastName + ", branch=" + branch + ", nationality="
				+ nationality + ", grade=" + grade + ", subject=" + subject + ", dob=" + dob + "]";
	}
	
}
