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
package com.mebigfatguy.damus.gui;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import com.mebigfatguy.damus.data.ForecastDataType;
import com.mebigfatguy.damus.data.Metric;
import com.mebigfatguy.damus.data.PredictionModel;
import com.mebigfatguy.damus.gui.model.NumberDocument;
import com.mebigfatguy.damus.main.Context;

public class TrainingValueCellRenderer implements TableCellRenderer {

	private final ForecastDataType forecastDataType;
	private final JPanel percentPanel;
	private final JPanel realPanel;
	private final JPanel yesNoPanel;
	
	private final JSlider percentValue;
	private final JTextField realValue;
	private final JCheckBox yesNoValue;
	
	public TrainingValueCellRenderer(ForecastDataType dataType) {
		forecastDataType = dataType;
		
		percentPanel = new JPanel();
		percentValue = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 50);
		percentValue.setMajorTickSpacing(25);
		percentValue.setMinorTickSpacing(5);
		percentValue.setPaintTicks(true);
		percentValue.setPaintLabels(true);
		percentPanel.add(percentValue);
		percentPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		percentPanel.setEnabled(true);
		
		realPanel = new JPanel();
		realValue = new JTextField(6);
		realValue.setDocument(new NumberDocument());
		realValue.setText("0");
		realValue.setAlignmentX(Component.RIGHT_ALIGNMENT);
		realPanel.add(realValue);
		realPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		realPanel.setEnabled(true);
		
		yesNoPanel = new JPanel();
		yesNoValue = new JCheckBox();
		yesNoPanel.add(yesNoValue);
		yesNoPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		yesNoPanel.setEnabled(true);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

		PredictionModel model = Context.instance().getPredictionModel();
		Metric metric;
		
		if (forecastDataType == ForecastDataType.Metric)
			metric = model.getMetric(row);
		else
			metric = model.getResult(row);
		
		JPanel panel;
		
		switch (metric.getType()) {
			case Percent:
				Integer percent = (Integer) value;
				percentValue.setValue(percent.intValue());
				panel = percentPanel;
			break;
			
			case Real:
				Double real = (Double) value;
				realValue.setText(String.valueOf(real));
				realValue.setSelectionStart(0);
				realValue.setSelectionEnd(Integer.MAX_VALUE);
				panel = realPanel;
			break;
			
			case YesNo:
			default:
				Boolean yesNo = (Boolean) value;
				yesNoValue.setSelected(yesNo.booleanValue());
				panel = yesNoPanel;
			break;
		}
		
		return panel;
	}
}
