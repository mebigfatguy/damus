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

import java.io.Serializable;

public class Metric implements Serializable {
	
	private static final long serialVersionUID = -8043402665535871850L;
	
	private String metricName;
	private MetricType metricType;
	
	public Metric(String name, MetricType type) {
		metricName = name;
		metricType = type;
	}
	
	public String getName() {
		return metricName;
	}
	
	public void setName(String name) {
		metricName = name;
	}
	
	public MetricType getType() {
		return metricType;
	}
	
	public void setType(MetricType type) {
		metricType = type;
	}
	
	@Override
	public String toString() {
		return "Metric[" + metricName + ": " + metricType + "]";
	}
}
