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
package com.mebigfatguy.damus.data;

import java.util.ArrayList;
import java.util.List;

public class TrainingData implements Cloneable {
	private List<TrainingItem> items = new ArrayList<TrainingItem>();
	private int currentIndex = 0;
	
	@Override
	public Object clone() {
		try {
			TrainingData data = (TrainingData)super.clone();
			data.items = new ArrayList<TrainingItem>();
			for (TrainingItem item : items) {
				if (item.getDescription() != null) {
					data.items.add(item);
				}
			}
			
			return data;
		} catch (CloneNotSupportedException cnse) {
			return new TrainingData();
		}
	}
	
	public void addItem(TrainingItem item) {
		items.add(item);
	}
	
	public void removeItem(TrainingItem item) {
		items.remove(item);
	}
	
	public TrainingItem getItem(int i) {
		return items.get(i);
	}
	
	public int getNumItems() {
		return items.size();
	}
	
	public void setCurrentIndex(int index) {
		currentIndex = index;
	}
	
	public int getCurrentIndex() {
		return currentIndex;
	}
}
