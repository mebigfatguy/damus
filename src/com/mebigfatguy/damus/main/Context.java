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
package com.mebigfatguy.damus.main;

import java.io.File;

import com.mebigfatguy.damus.data.PredictionModel;
import com.mebigfatguy.damus.data.TrainingData;
import com.mebigfatguy.damus.gui.DamusFrame;

public class Context {
	
	private static final Context instance = new Context();
	
	private DamusFrame damusFrame;
	private PredictionModel predictionModel;
	private TrainingData trainingData;
	private File file;
	private boolean isDirty = false;
	
	
	public static final Context instance() {
		return instance;
	}
	
	private Context() {
	}
	
	public void setFrame(DamusFrame frame) {
		damusFrame = frame;
	}
	
	public DamusFrame getFrame() {
		return damusFrame;
	}

	public PredictionModel getPredictionModel() {
		return predictionModel;
	}

	public void setPredictionModel(PredictionModel model) {
		predictionModel = model;
	}
	
	public TrainingData getTrainingData() {
		return trainingData;
	}
	
	public void setTrainingData(TrainingData data) {
		trainingData = data;
	}
	
	public void setFile(File f) {
		file = f;
	}
	
	public File getFile() {
		return file;
	}

	public boolean isDirty() {
		return isDirty;
	}
	
	public void setDirty(boolean dirty) {
		isDirty = dirty;
	}
}
