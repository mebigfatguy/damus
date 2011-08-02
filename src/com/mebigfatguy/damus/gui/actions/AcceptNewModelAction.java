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
import com.mebigfatguy.damus.gui.PanelType;
import com.mebigfatguy.damus.gui.PanelViewer;
import com.mebigfatguy.damus.gui.TrainingPanel;
import com.mebigfatguy.damus.main.Context;

public class AcceptNewModelAction extends AbstractAction {

	private static final long serialVersionUID = -510302617981900753L;
	private final PanelViewer panelViewer;
	
	public AcceptNewModelAction(PanelViewer viewer) {
		super(DamusBundle.getString(DamusBundle.OK));
		panelViewer = viewer;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		panelViewer.closePanel(PanelType.NEWMODEL);
		TrainingData data = Context.instance().getTrainingData();
		data.addItem(new TrainingItem());
		data.setCurrentIndex(0);
		panelViewer.openPanel(PanelType.TRAINMODEL, new TrainingPanel(panelViewer, true), true);
	}
}