package com.bilygine.analyzer.analyze.steps;


public abstract class DataPreparationStep implements Step {

	private int rows;

	public DataPreparationStep(int rows) {
		this.rows = rows;
	}

	public int getRowCount() {
		return this.rows;
	}

}
