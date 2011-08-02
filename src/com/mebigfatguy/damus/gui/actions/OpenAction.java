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
package com.mebigfatguy.damus.gui.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.mebigfatguy.damus.bundle.DamusBundle;
import com.mebigfatguy.damus.data.Metric;
import com.mebigfatguy.damus.data.PredictionModel;
import com.mebigfatguy.damus.data.TrainingData;
import com.mebigfatguy.damus.data.TrainingItem;
import com.mebigfatguy.damus.gui.NewModelPanel;
import com.mebigfatguy.damus.gui.PanelType;
import com.mebigfatguy.damus.gui.PanelViewer;
import com.mebigfatguy.damus.io.DamusIO;
import com.mebigfatguy.damus.main.Context;

public class OpenAction extends AbstractAction {

	private static final long serialVersionUID = -268582088845178073L;
	
	private final PanelViewer panelViewer;
	
	public OpenAction(PanelViewer viewer) {
		super(DamusBundle.getString(DamusBundle.OPEN));
		panelViewer = viewer;
	}
	
	public void actionPerformed(ActionEvent e) {
		Context context = Context.instance();
		File f = null;
		try {
			f = DamusIO.getOpenFile();
			
			if (f != null) {
				DamusIO dio = new DamusIO();
				dio.read(f);

				PredictionModel predictModel = new PredictionModel();
				for (Metric metric : dio.getMetrics()) {
					predictModel.addMetric(metric);
				}
				for (Metric result : dio.getResults()) {
					predictModel.addResult(result);
				}
				context.setPredictionModel(predictModel);
				
				TrainingData trainingData = new TrainingData();
				for (TrainingItem item : dio.getTrainingItems()) {
					trainingData.addItem(item);
				}
				
				context.setTrainingData(trainingData);

				panelViewer.closeAllPanels();
				JPanel newPanel = createNewPanel();
				panelViewer.openPanel(PanelType.NEWMODEL, newPanel, false);	
				context.setFile(f);
				context.setDirty(false);
			}
		} catch (IOException ioe) {
			String msg = MessageFormat.format(DamusBundle.getString(DamusBundle.DAMUS_OPEN_FAILURE), (f != null) ? f.getPath() : "?");
			JOptionPane.showMessageDialog(context.getFrame(), msg);
		}

	}
	
	private JPanel createNewPanel() {
		return new NewModelPanel(panelViewer, true);
	}

}
