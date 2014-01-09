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
import com.mebigfatguy.damus.data.TrainingData;
import com.mebigfatguy.damus.main.Context;

public class ReviewItemsTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -5748653190162875726L;
	private final TrainingData data = Context.instance().getTrainingData();
	
	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public int getRowCount() {
		return data.getNumItems();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0)
			return data.getItem(rowIndex).getDescription();
		return null;
	}

	@Override
	public String getColumnName(int column) {
		if (column == 0)
			return DamusBundle.getString(DamusBundle.DESCRIPTION);

		return "";
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}
}
