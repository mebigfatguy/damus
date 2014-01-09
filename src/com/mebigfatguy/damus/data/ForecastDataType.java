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

import java.text.MessageFormat;

import com.mebigfatguy.damus.bundle.DamusBundle;

public enum ForecastDataType {
	Metric(DamusBundle.METRIC, DamusBundle.ADD_METRIC, DamusBundle.DELETE_METRIC),
	Result(DamusBundle.RESULT, DamusBundle.ADD_RESULT, DamusBundle.DELETE_RESULT);
	
	private static int sNum = 0;
	private String defaultKey;
	private String addKey;
	private String deleteKey;
	
	private ForecastDataType(String def, String add, String delete) {
		defaultKey = def;
		addKey = add;
		deleteKey = delete;
	}
	
	public String getDefaultString() {
		synchronized(ForecastDataType.class) {
			return MessageFormat.format(DamusBundle.getString(defaultKey), Integer.valueOf(++sNum));
		}
	}
	
	public String getAddString() {
		return DamusBundle.getString(addKey);
	}
	
	public String getDeleteString() {
		return DamusBundle.getString(deleteKey);
	}
}
