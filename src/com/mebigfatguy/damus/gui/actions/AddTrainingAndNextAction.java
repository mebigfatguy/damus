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

import javax.swing.AbstractAction;

import com.mebigfatguy.damus.bundle.DamusBundle;
import com.mebigfatguy.damus.data.TrainingData;
import com.mebigfatguy.damus.data.TrainingItem;
import com.mebigfatguy.damus.gui.PanelViewer;
import com.mebigfatguy.damus.gui.TrainingPanel;
import com.mebigfatguy.damus.main.Context;

public class AddTrainingAndNextAction extends AbstractAction {

	private static final long serialVersionUID = 3880351786349958924L;
	private final PanelViewer panelViewer;
	private final TrainingPanel trainingPanel;

	public AddTrainingAndNextAction(PanelViewer viewer, TrainingPanel panel) {
		super(DamusBundle.getString(DamusBundle.ADD_TRAINING_NEXT));
		panelViewer = viewer;
		trainingPanel = panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Context context = Context.instance();
		TrainingData data = Context.instance().getTrainingData();
		if (trainingPanel.populateTrainingItem(data.getItem(data.getNumItems() - 1))) {
			context.setDirty(true);
			data.addItem(new TrainingItem());
			data.setCurrentIndex(data.getNumItems() - 1);
			trainingPanel.reset();
		}
	}

}