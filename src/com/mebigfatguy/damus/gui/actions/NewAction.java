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
import javax.swing.JPanel;

import com.mebigfatguy.damus.bundle.DamusBundle;
import com.mebigfatguy.damus.data.PredictionModel;
import com.mebigfatguy.damus.data.TrainingData;
import com.mebigfatguy.damus.gui.NewModelPanel;
import com.mebigfatguy.damus.gui.PanelType;
import com.mebigfatguy.damus.gui.PanelViewer;
import com.mebigfatguy.damus.io.DamusIO;
import com.mebigfatguy.damus.main.Context;

public class NewAction extends AbstractAction {

	private static final long serialVersionUID = -1656403699359626055L;
	
	private final PanelViewer panelViewer;
	
	public NewAction(PanelViewer viewer) {
		super(DamusBundle.getString(DamusBundle.NEW));
		panelViewer = viewer;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (DamusIO.checkSave()) {
			Context context = Context.instance();
			context.setPredictionModel(new PredictionModel());
			context.setTrainingData(new TrainingData());
			JPanel newPanel = createNewPanel();
			panelViewer.openPanel(PanelType.NEWMODEL, newPanel, false);	
			context.setDirty(false);
		}
	}
	
	private JPanel createNewPanel() {
		return new NewModelPanel(panelViewer, true);
	}

}
