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

import javax.swing.AbstractAction;
import javax.swing.JTable;

import com.mebigfatguy.damus.bundle.DamusBundle;
import com.mebigfatguy.damus.data.ForecastDataType;
import com.mebigfatguy.damus.gui.model.MetricsTableModel;
import com.mebigfatguy.damus.main.Context;

public class DeleteForecastDataTypeAction extends AbstractAction {

	private static final long serialVersionUID = 3155375105828862712L;

	private final JTable metricTable;
	
	public DeleteForecastDataTypeAction(JTable table, ForecastDataType type) {
		super(DamusBundle.getString((type == ForecastDataType.Metric) ? DamusBundle.DELETE_METRIC : DamusBundle.DELETE_RESULT));
		metricTable = table;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int[] selectedRows = metricTable.getSelectedRows();
		MetricsTableModel model = (MetricsTableModel)metricTable.getModel();
		for (int i = selectedRows.length - 1; i >= 0; i--) {
			model.removeMetric(selectedRows[i]);
		}
		Context context = Context.instance();
		context.setDirty(true);
	}
}
