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
import com.mebigfatguy.damus.gui.PanelViewer;

public class RemovePanelAction extends AbstractAction {

	private static final long serialVersionUID = -6777960501219927732L;
	
	private PanelViewer panelViewer;
	private JPanel panel;
	
	public RemovePanelAction(PanelViewer viewer, JPanel p) {
		super(DamusBundle.getString(DamusBundle.CANCEL));
		panelViewer = viewer;
		panel = p;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
	}

}
