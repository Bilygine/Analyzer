package com.bilygine.analyzer.analyze.steps.preparation;

import com.bilygine.analyzer.analyze.result.ResultColumn;

import java.util.ArrayList;
import java.util.List;

public class IdDataPrepStep extends DataPrepStep {

	public IdDataPrepStep(int rows) {
		super(rows);
	}

	@Override
	public String getName() {
		return "Unique Key";
	}

	@Override
	public List<ResultColumn> call() throws Exception {
		ResultColumn uniqueKey = new ResultColumn("id");
	}
}
