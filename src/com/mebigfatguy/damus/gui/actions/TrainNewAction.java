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
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPanel;

import com.mebigfatguy.damus.bundle.DamusBundle;
import com.mebigfatguy.damus.data.TrainingData;
import com.mebigfatguy.damus.data.TrainingItem;
import com.mebigfatguy.damus.gui.PanelType;
import com.mebigfatguy.damus.gui.PanelViewer;
import com.mebigfatguy.damus.gui.TrainingPanel;
import com.mebigfatguy.damus.main.Context;


public class TrainNewAction extends AbstractAction {

	private static final long serialVersionUID = -5199894453893029822L;
	
	private final PanelViewer panelViewer;
	
	public TrainNewAction(PanelViewer viewer) {
		super(DamusBundle.getString(DamusBundle.TRAIN));
		panelViewer = viewer;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JCheckBoxMenuItem item = (JCheckBoxMenuItem)e.getSource();
		if (item.isSelected()) {
			JPanel newPanel = createTrainingPanel();
			panelViewer.openPanel(PanelType.TRAINMODEL, newPanel, true);
		} else {
			panelViewer.closePanel(PanelType.TRAINMODEL);
		}
	}
	
	private JPanel createTrainingPanel() {
		TrainingData data = Context.instance().getTrainingData();
		data.addItem(new TrainingItem());
		data.setCurrentIndex(data.getNumItems() - 1);
		return new TrainingPanel(panelViewer, true);
	}
}
