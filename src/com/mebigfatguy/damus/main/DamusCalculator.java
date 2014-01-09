/*
 * damus - a forecasting tool
 * Copyright (C) 2010-2014 Dave Brosius
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations
 * under the License.
 */
package com.mebigfatguy.damus.main;

import java.math.BigDecimal;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

import com.mebigfatguy.damus.data.Metric;
import com.mebigfatguy.damus.data.PredictionModel;
import com.mebigfatguy.damus.data.TrainingData;
import com.mebigfatguy.damus.data.TrainingItem;

public class DamusCalculator {

    private final TrainingItem trainingItem;
    public DamusCalculator(TrainingItem item) {
        trainingItem = item;
    }

    public boolean calc() {
        return calcLinearRegression();
    }

    private boolean calcLinearRegression() {
        try {
            Context context = Context.instance();
            PredictionModel model = context.getPredictionModel();
            TrainingData data = context.getTrainingData();

            int numMetrics = model.getNumMetrics();
            int numResults = model.getNumResults();
            int numItems = data.getNumItems();

            OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();

            double [][] xx = new double[numItems][];
            for (int i = 0; i < numItems; i++) {
                double[] x = new double[numMetrics];
                for (int m = 0; m < numMetrics; m++) {
                    Metric metric = model.getMetric(m);
                    double value;
                    switch (metric.getType()) {
                        case Percent:
                            value = ((Number)data.getItem(i).getValue(metric)).doubleValue();
                            break;

                        case Real:
                            value = ((BigDecimal)data.getItem(i).getValue(metric)).doubleValue();
                            break;

                        case YesNo:
                            value = ((Boolean)data.getItem(i).getValue(metric)).booleanValue() ? 1.0 : 0.0;
                            break;

                        default:
                            value = 0.0;
                            break;
                    }
                    x[m] = value;
                }
                xx[i] = x;
            }

            for (int r = 0; r < numResults; r++) {
                Metric metric = model.getResult(r);
                double[] y = new double[numItems];
                for (int i = 0; i < numItems; i++) {
                    double value;
                    switch (metric.getType()) {
                        case Percent:
                            value = ((Number)data.getItem(i).getValue(metric)).doubleValue();
                            break;

                        case Real:
                            value = ((BigDecimal)data.getItem(i).getValue(metric)).doubleValue();
                            break;

                        case YesNo:
                            value = ((Boolean)data.getItem(i).getValue(metric)).booleanValue() ? 1.0 : 0.0;
                            break;

                        default:
                            value = 0.0;
                            break;
                    }
                    y[i] = value;
                }

                regression.newSampleData(y, xx);
                double result = regression.estimateRegressandVariance();
                trainingItem.setValue(metric, new BigDecimal(result));
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
