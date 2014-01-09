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
package com.mebigfatguy.damus.data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mebigfatguy.damus.main.Context;

public class TrainingItem implements Cloneable {
    private String itemDescription;
    private Map<Metric, Object> metricData = new HashMap<Metric, Object>();

    public TrainingItem() {
        PredictionModel model = Context.instance().getPredictionModel();
        if (model != null) {
            int numMetrics = model.getNumMetrics();
            for (int i = 0; i < numMetrics; i++) {
                Metric metric = model.getMetric(i);
                switch (metric.getType()) {
                    case Percent:
                        metricData.put(metric, Integer.valueOf(50));
                        break;

                    case Real:
                        metricData.put(metric, new BigDecimal("0"));
                        break;

                    case YesNo:
                        metricData.put(metric, Boolean.FALSE);
                        break;
                }
            }
            int numResults = model.getNumResults();
            for (int i = 0; i < numResults; i++) {
                Metric metric = model.getResult(i);
                switch (metric.getType()) {
                    case Percent:
                        metricData.put(metric, Integer.valueOf(50));
                        break;

                    case Real:
                        metricData.put(metric, new BigDecimal(0));
                        break;

                    case YesNo:
                        metricData.put(metric, Boolean.FALSE);
                        break;
                }
            }
        }
    }

    public void setDescription(String description) {
        itemDescription = description;
    }

    public String getDescription() {
        return itemDescription;
    }

    public Object getValue(Metric metric) {
        return metricData.get(metric);
    }

    public void setValue(Metric metric, Object value) {
        metricData.put(metric, value);
    }

    @Override
    public Object clone() {
        try {
            TrainingItem clone = (TrainingItem)super.clone();
            clone.metricData = new HashMap<Metric, Object>(metricData);

            return clone;
        } catch (CloneNotSupportedException cnse) {
            throw new Error("Clone Not Supported on TrainingItem");
        }
    }
}
