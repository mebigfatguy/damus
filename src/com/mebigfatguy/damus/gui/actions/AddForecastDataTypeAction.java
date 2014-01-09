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
package com.mebigfatguy.damus.gui.actions;

import java.awt.event.ActionEvent;
import java.text.MessageFormat;

import javax.swing.AbstractAction;
import javax.swing.JTable;

import com.mebigfatguy.damus.bundle.DamusBundle;
import com.mebigfatguy.damus.data.ForecastDataType;
import com.mebigfatguy.damus.data.MetricType;
import com.mebigfatguy.damus.gui.model.MetricsTableModel;
import com.mebigfatguy.damus.main.Context;

public class AddForecastDataTypeAction extends AbstractAction {

	private static final long serialVersionUID = -5282542139374153269L;
	private static int metricNum = 0;
	
	private final JTable metricTable;
	
	public AddForecastDataTypeAction(JTable table, ForecastDataType type) {
		super(type.getAddString());
		metricTable = table;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		MetricsTableModel model = (MetricsTableModel)metricTable.getModel();
		String name;
		
		synchronized(AddForecastDataTypeAction.class) {
			name = MessageFormat.format(DamusBundle.getString(DamusBundle.METRIC), Integer.valueOf(++metricNum));
		}
		model.addRow(name, MetricType.Percent);
		Context context = Context.instance();
		context.setDirty(true);
	}

}
