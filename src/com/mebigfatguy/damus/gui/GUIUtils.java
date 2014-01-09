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
package com.mebigfatguy.damus.gui;

import java.awt.Dimension;

import javax.swing.JComponent;

public class GUIUtils {

	private GUIUtils() {
	}
	
	public static void sizeUniformly(JComponent... components) {
		for (JComponent comp : components) {
			comp.setPreferredSize(null);
			comp.setMinimumSize(null);
			comp.setMaximumSize(null);
		}
		
		int maxW = 0;
		int maxH = 0;
		
		for (JComponent comp : components) {
			Dimension d = comp.getPreferredSize();
			if (d.width > maxW) {
				maxW = d.width;
			}
			if (d.height > maxH) {
				maxH = d.height;
			}
		}
		
		Dimension d = new Dimension(maxW, maxH);
		for (JComponent comp : components) {
			comp.setPreferredSize(d);
			comp.setMaximumSize(d);
			comp.setMinimumSize(d);
		}
	}
}
