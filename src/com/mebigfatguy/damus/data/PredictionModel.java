/*
 * damus - a forecasting tool
 * Copyright (C) 2010 Dave Brosius
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PredictionModel implements Serializable {
	
	private static final long serialVersionUID = -585343437079409170L;
	
	private List<Metric> metrics;
	private List<Metric> results;
	
	public PredictionModel() {
		metrics = new ArrayList<Metric>();
		results = new ArrayList<Metric>();
	}
	
	public PredictionModel(List<Metric> predictionMetrics, List<Metric> predictionResults) {
		metrics = predictionMetrics;
		results = predictionResults;
	}
	
	public void addMetric(Metric metric) {
		metrics.add(metric);
	}
	
	public void addResult(Metric result) {
		results.add(result);
	}
	
	public void deleteMetric(int idx) {
		metrics.remove(idx);
	}
	
	public void deleteResult(int idx) {
		results.remove(idx);
	}
	
	public Metric getMetric(int idx) {
		return metrics.get(idx);
	}
	
	public Metric getResult(int idx) {
		return results.get(idx);
	}
	
	public int getNumMetrics() {
		return metrics.size();
	}
	
	public int getNumResults() {
		return results.size();
	}
}
