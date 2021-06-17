package com.student.model;

import java.util.Arrays;
import java.util.IntSummaryStatistics;

public class SubjectMarks {
	private int maths, biology, computers;

	public SubjectMarks() {
	}
	
	public SubjectMarks(int maths, int biology, int computers) {
		this.maths = maths;
		this.biology = biology;
		this.computers = computers;
		
	}

	public int getMaths() {
		return maths;
	}

	public void setMaths(int maths) {
		this.maths = maths;
	}

	public int getBiology() {
		return biology;
	}

	public void setBiology(int biology) {
		this.biology = biology;
	}

	public int getComputers() {
		return computers;
	}

	public void setComputers(int computers) {
		this.computers = computers;
	}

	public int getAverageMarks() {
		IntSummaryStatistics stats =Arrays.asList(maths, biology, computers).stream()
			    .mapToInt(Integer::intValue)
			    .summaryStatistics();
		return (int) stats.getAverage();
	}

	@Override
	public String toString() {
		return "\nSubjectMarks [maths=" + maths + ", biology=" + biology + ", computers=" + computers +", avg= "+getAverageMarks()+ "]";
	}
}
