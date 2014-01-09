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
package com.mebigfatguy.damus.gui.model;

import javax.swing.table.AbstractTableModel;

import com.mebigfatguy.damus.bundle.DamusBundle;
import com.mebigfatguy.damus.data.ForecastDataType;
import com.mebigfatguy.damus.data.Metric;
import com.mebigfatguy.damus.data.PredictionModel;
import com.mebigfatguy.damus.data.TrainingItem;
import com.mebigfatguy.damus.main.Context;

public class PredictionTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -8900184057917667615L;
	private final ForecastDataType forecastDataType;
	private final PredictionModel model = Context.instance().getPredictionModel();
	private final TrainingItem trainingItem;
	
	public PredictionTableModel(ForecastDataType dataType, TrainingItem item) {
		forecastDataType = dataType;
		trainingItem = item;
	}
	
	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		if (forecastDataType == ForecastDataType.Metric)
			return model.getNumMetrics();
		else
			return model.getNumResults();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Metric metric;
		
		if (forecastDataType == ForecastDataType.Metric)
			metric = model.getMetric(rowIndex);
		else
			metric = model.getResult(rowIndex);
		
		if (columnIndex == 0) {
			return metric.getName();
		} else {
			return trainingItem.getValue(metric);
		}
	}
	
	@Override
	public String getColumnName(int column) {
		if (column == 0) {
			if (forecastDataType == ForecastDataType.Metric)
				return DamusBundle.getString(DamusBundle.METRIC_NAME);
			else
				return DamusBundle.getString(DamusBundle.RESULT_NAME);
		} else {
			if (forecastDataType == ForecastDataType.Metric)
				return DamusBundle.getString(DamusBundle.METRIC_VALUE);
			else
				return DamusBundle.getString(DamusBundle.RESULT_VALUE);
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 0)
			return String.class;
		else
			return Object.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return (forecastDataType == ForecastDataType.Metric) && (columnIndex == 1);
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
	}
}
