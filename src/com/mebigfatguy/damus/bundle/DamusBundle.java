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
package com.mebigfatguy.damus.bundle;

import java.util.ResourceBundle;

public class DamusBundle {
		
	public static final String TITLE = "damus.title";
	public static final String FILE = "damus.file";
	public static final String NEW = "damus.new";
	public static final String OPEN = "damus.open";
	public static final String CLOSE = "damus.close";
	public static final String SAVE = "damus.save";
	public static final String SAVE_AS = "damus.saveas";
	public static final String QUIT = "damus.quit";
	public static final String ADD_METRIC = "damus.addmetric";
	public static final String DELETE_METRIC = "damus.deletemetric";
	public static final String ADD_RESULT = "damus.addresult";
	public static final String DELETE_RESULT = "damus.deleteresult";
	public static final String METRIC_NAME = "damus.metricname";
	public static final String METRIC_TYPE = "damus.metrictype";
	public static final String METRIC_VALUE = "damus.metricvalue";
	public static final String METRIC = "damus.metric";
	public static final String RESULT = "damus.result";
	public static final String PERCENT = "damus.percent";
	public static final String REAL = "damus.real";
	public static final String YESNO = "damus.yesno";
	public static final String CREATE_MODEL = "damus.createmodel";
	public static final String CANCEL = "damus.cancel";
	public static final String CLOSE_PANEL = "damus.closepanel";
	public static final String OK = "damus.ok";
	public static final String MODEL = "damus.predictionmodel";
	public static final String RESULT_NAME = "damus.resultname";
	public static final String RESULT_TYPE = "damus.resulttype";
	public static final String RESULT_VALUE = "damus.resultvalue";
	public static final String METRICS = "damus.metrics";
	public static final String RESULTS = "damus.results";
	public static final String DATA = "damus.data";
	public static final String REVIEW = "damus.review";
	public static final String TRAIN = "damus.train";
	public static final String PREDICT = "damus.predict";
	public static final String TRAINEDITEMS = "damus.traineditems";
	public static final String DELETE = "damus.delete";
	public static final String TRAINITEM = "damus.trainitem";
	public static final String FINISH = "damus.finish";
	public static final String DESCRIPTION = "damus.description";
	public static final String ADD_TRAINING_FINISH = "damus.addtrainingfinish";
	public static final String ADD_TRAINING_NEXT = "damus.addtrainingnext";
	public static final String DAMUS_FILE_DESCRIPTION = "damus.filedescription";
	public static final String DAMUS_OVERWRITE_WARNING = "damus.overwritewarning";
	public static final String DAMUS_OPEN_FAILURE = "damus.fileopenfailure";
	public static final String DAMUS_SAVE_FAILURE = "damus.filesavefailure";	
	public static final String UNTITLED_FILE = "damus.untitledfile";
	public static final String ASK_SAVE = "damus.asksave";
	
	private static ResourceBundle rb = ResourceBundle.getBundle("com/mebigfatguy/damus/bundle/resources");
	
	private DamusBundle()
	{
	}
	
	public static String getString(String key) {
		return rb.getString(key);
	}

}
