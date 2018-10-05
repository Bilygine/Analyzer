package com.bilygine.analyzer.analyze;

import com.bilygine.analyzer.analyze.result.ResultColumn;

import java.util.List;
import java.util.concurrent.Callable;

public interface Step extends Callable<List<ResultColumn>> {

    /**
     * @return the name of the steps
     */
    String getName();
}
