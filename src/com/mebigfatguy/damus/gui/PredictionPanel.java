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

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import com.mebigfatguy.damus.bundle.DamusBundle;
import com.mebigfatguy.damus.data.ForecastDataType;
import com.mebigfatguy.damus.data.TrainingItem;
import com.mebigfatguy.damus.gui.actions.ClosePredictionAction;
import com.mebigfatguy.damus.gui.model.PredictionTableModel;
import com.mebigfatguy.damus.main.DamusCalculator;

public class PredictionPanel extends JPanel {

	private static final long serialVersionUID = -5723506056156479994L;
	private final PanelViewer panelViewer;
	private JTextField descriptionField;
	private JTable predictionTable;
	private JTable resultsTable;
	private JButton closeButton;
	private final TrainingItem trainingItem = new TrainingItem();
	private PredictionTableModel resultsModel;
	
	public PredictionPanel(PanelViewer viewer) {
		panelViewer = viewer;
		initComponents();
	}
	
	private void initComponents() {
		setLayout(new BorderLayout(4, 4));
		
		add(createDescriptionPanel(), BorderLayout.NORTH);
		add(createPredictionPanel(), BorderLayout.CENTER);
		add(createCtrlPanel(), BorderLayout.SOUTH);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				updateResults();
				descriptionField.requestFocus();
			}
		});
	}
	
	private JPanel createDescriptionPanel() {
		JPanel descPanel = new JPanel();
		
		descPanel.setLayout(new BorderLayout(4, 4));
		JLabel descLabel = new JLabel(DamusBundle.getString(DamusBundle.DESCRIPTION));
		descPanel.add(descLabel, BorderLayout.WEST);
		descriptionField = new JTextField(20);
		descLabel.setLabelFor(descriptionField);
		descPanel.add(descriptionField, BorderLayout.CENTER);
		descPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		return descPanel;
	}
	
	private JPanel createPredictionPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		{
			JPanel metricsPanel = new JPanel();
			metricsPanel.setLayout(new BorderLayout(4, 4));
			predictionTable = new JTable(new PredictionTableModel(ForecastDataType.Metric, trainingItem));
			predictionTable.setRowHeight(predictionTable.getRowHeight() + 14);
			TableColumn column = predictionTable.getColumnModel().getColumn(1);
			column.setCellRenderer(new TrainingValueCellRenderer(ForecastDataType.Metric));
			TableCellEditor editor = new TrainingValueCellEditor(ForecastDataType.Metric);
			editor.addCellEditorListener(new CellEditorListener() {

				@Override
				public void editingCanceled(ChangeEvent e) {
					updateResults();		
				}

				@Override
				public void editingStopped(ChangeEvent e) {
					updateResults();
				}
			});
			column.setCellEditor(editor);
			metricsPanel.add(new JScrollPane(predictionTable), BorderLayout.CENTER);
			metricsPanel.setBorder(BorderFactory.createTitledBorder(DamusBundle.getString(DamusBundle.METRICS)));
			panel.add(metricsPanel);
		}
		
		{
			JPanel resultsPanel = new JPanel();
			resultsPanel.setLayout(new BorderLayout(4, 4));
			resultsModel = new PredictionTableModel(ForecastDataType.Result, trainingItem);
			resultsTable = new JTable(resultsModel);
			resultsTable.setRowHeight(resultsTable.getRowHeight() + 14);
			TableColumn column = resultsTable.getColumnModel().getColumn(1);
			column.setCellRenderer(new TrainingValueCellRenderer(ForecastDataType.Result));
			column.setCellEditor(new TrainingValueCellEditor(ForecastDataType.Result));
			resultsPanel.add(new JScrollPane(resultsTable), BorderLayout.CENTER);
			resultsPanel.setBorder(BorderFactory.createTitledBorder(DamusBundle.getString(DamusBundle.RESULTS)));
			panel.add(resultsPanel);
		}
		
		return panel;
	}
	
	private JPanel createCtrlPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		closeButton = new JButton(new ClosePredictionAction(panelViewer));
		
		panel.add(Box.createHorizontalGlue());
		panel.add(closeButton);
		
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		return panel;
	}
	
	private void updateResults() {
		DamusCalculator calc = new DamusCalculator(trainingItem);
		if (calc.calc()) {
			resultsModel.fireTableStructureChanged();
		}		
	}
}
