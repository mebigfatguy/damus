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
package com.mebigfatguy.damus.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.mebigfatguy.damus.bundle.DamusBundle;
import com.mebigfatguy.damus.data.Metric;
import com.mebigfatguy.damus.data.MetricType;
import com.mebigfatguy.damus.data.PredictionModel;
import com.mebigfatguy.damus.data.TrainingData;
import com.mebigfatguy.damus.data.TrainingItem;
import com.mebigfatguy.damus.main.Context;

public class DamusIO {
	
	private static final String TAB = "\t";
	private static final String EXT = ".dms";
	
	private Metric[] metrics;
	private Metric[] results;
	private TrainingItem[] items;
	
	public DamusIO() {
	}
	
	public static boolean checkSave() {
		try {
			Context context = Context.instance();
			if (context.isDirty()) {
				AskSaveType action = DamusIO.askSave();
				switch (action) {
					case YES:
						File f = context.getFile();
						if (f != null)
							f = saveFile();
						else
							f = saveFileAs();
						return f != null;
					
					case NO:
						return true;
					
					case CANCEL:
						return false;
				}
			}
			
			return true;
		} catch (IOException ioe) {
			Context context = Context.instance();
			File f = context.getFile();
			String name = (f != null) ? f.getPath() : DamusBundle.getString(DamusBundle.UNTITLED_FILE);
			String msg = MessageFormat.format(DamusBundle.getString(DamusBundle.DAMUS_SAVE_FAILURE), name);
			JOptionPane.showMessageDialog(context.getFrame(), msg);	
			return false;
		}
	}
	
	private static AskSaveType askSave() {
		Context context = Context.instance();
		
		File f = context.getFile();
		String name;
		
		if (f == null) {
			name = DamusBundle.getString(DamusBundle.UNTITLED_FILE);
		} else {
			name = f.getPath();
		}
		
		String msg = MessageFormat.format(DamusBundle.getString(DamusBundle.ASK_SAVE), name);
		int option = JOptionPane.showConfirmDialog(context.getFrame(), msg);
		switch (option) {
			case JOptionPane.OK_OPTION:
				return AskSaveType.YES;
			case JOptionPane.NO_OPTION:
				return AskSaveType.NO;
			default:
				return AskSaveType.CANCEL;
		}
	}
	
	public static File getSaveFile(File f) {
		
		JFileChooser fc = new JFileChooser();
		fc.setSelectedFile(f);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setFileFilter(new DamusFileFilter());
		
		int option = fc.showSaveDialog(Context.instance().getFrame());
		if (option == JFileChooser.APPROVE_OPTION) {
			f = fc.getSelectedFile();
			
			if (!f.getPath().endsWith(EXT)) {
				f = new File(f.getPath() + EXT);
			}
			if (checkOverwrite(f)) {
				return f;
			}
		}
		
		return null;
	}
	
	public static final File saveFile() throws IOException {
		Context context = Context.instance();
		File f = context.getFile();
		if (f == null) {
			f = DamusIO.getSaveFile(null);
			if (f != null) {
				context.setFile(f);
			}
		}
		if (f != null) {
			save(f);
			context.setDirty(false);
		}
		
		return f;
	}
	
	public static final File saveFileAs() throws IOException {
		Context context = Context.instance();
		File f = DamusIO.getSaveFile(context.getFile());
		if (f != null) {
			save(f);
			context.setFile(f);
			context.setDirty(false);
		}
		
		return f;
	}
	
	public static File getOpenFile() {
		
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setFileFilter(new DamusFileFilter());
		
		int option = fc.showOpenDialog(Context.instance().getFrame());
		if (option == JFileChooser.APPROVE_OPTION) {
			return fc.getSelectedFile();
		}
		
		return null;
	}
	
	private static void save(File f) throws IOException {
		File temp = File.createTempFile("DIO_", ".csv");
		Context context = Context.instance();
		PredictionModel model = context.getPredictionModel();
		TrainingData data = context.getTrainingData();
		writeData(temp, model, data);
		if (f != null) {
			f.delete();
			temp.renameTo(f);
		}
	}
	
	public void read(File f) throws IOException {
		BufferedReader br = null;
		String line = null;
		String[] parts = null;
		
		try {
			br = new BufferedReader(new FileReader(f));
			//skip header
		    line = br.readLine();
			line = br.readLine();
			
			//read metrics
			line = br.readLine();
			parts = line.split(TAB);
			if (parts.length != 2) {
				throw new IOException("Failed reading number of metrics: " + line);
			}
			int numMetrics = Integer.parseInt(parts[1]);
			metrics = new Metric[numMetrics];
			for (int i = 0; i < numMetrics; i++) {
				line = br.readLine();
				parts = line.split(TAB);
				if (parts.length != 2) {
					throw new IOException("Failed reading metric: " + line);
				}
				Metric m = new Metric(parts[0], Enum.<MetricType>valueOf(MetricType.class, parts[1]));
				metrics[i] = m;
			}
			line = br.readLine();
			
			//read results
			line = br.readLine();
			parts = line.split(TAB);
			if (parts.length != 2) {
				throw new IOException("Failed reading number of results: " + line);
			}
			int numResults = Integer.parseInt(parts[1]);
			results = new Metric[numResults];
			for (int i = 0; i < numResults; i++) {
				line = br.readLine();
				parts = line.split(TAB);
				if (parts.length != 2) {
					throw new IOException("Failed reading result: " + line);
				}
				Metric m = new Metric(parts[0], Enum.<MetricType>valueOf(MetricType.class, parts[1]));
				results[i] = m;
			}
			line = br.readLine();
			
			//read training items
			line = br.readLine();
			parts = line.split(TAB);
			if (parts.length != 2) {
				throw new IOException("Failed reading number of training items: " + line);
			}
			int numItems = Integer.parseInt(parts[1]);
			int totalDetails = 1 + numMetrics + numResults;
			items = new TrainingItem[numItems];
			for (int i = 0; i < numItems; i++) {
				line = br.readLine();
				parts = line.split(TAB);
				if (parts.length != totalDetails) {
					throw new IOException("Failed reading item: " + line);
				}
				
				TrainingItem item = new TrainingItem();
				item.setDescription(parts[0]);
				int partIndex = 1;
				for (int m = 0; m < numMetrics; m++) {
					String value = parts[partIndex++];
					Metric metric = metrics[m];
					switch (metric.getType()) {
						case Percent:
							item.setValue(metric, Integer.valueOf(value));
						break;
						
						case Real:
							item.setValue(metric, Double.valueOf(value));
						break;
						
						case YesNo:
							item.setValue(metric, Boolean.valueOf(value));
						break;
					}
				}
				for (int r = 0; r < numResults; r++) {
					String value = parts[partIndex++];
					Metric result = results[r];
					switch (result.getType()) {
						case Percent:
							item.setValue(result, Integer.valueOf(value));
						break;
						
						case Real:
							item.setValue(result, Double.valueOf(value));
						break;
						
						case YesNo:
							item.setValue(result, Boolean.valueOf(value));
						break;
					}
				}
				items[i] = item;
			}
		} catch (Exception e) {
			throw new IOException("Failed reading Damus file : " + f.getPath(), e);
		} finally {
			Closer.close(br);
		}
	}
	
	public Metric[] getMetrics() {
		return metrics;
	}
	
	public Metric[] getResults() {
		return results;
	}
	
	public TrainingItem[] getTrainingItems() {
		return items;
	}
	
	private static boolean checkOverwrite(File f) {
		if (!f.exists()) {
			return true;
		}
		
		Context context = Context.instance();
		String msg = MessageFormat.format(DamusBundle.getString(DamusBundle.DAMUS_OVERWRITE_WARNING), f.getPath());
		int option = JOptionPane.showConfirmDialog(context.getFrame(), msg);
		return option == JOptionPane.OK_OPTION;
	}
	
	private static void writeData(File f, PredictionModel model, TrainingData data) throws IOException {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(f)));
			pw.println(DamusBundle.getString(DamusBundle.TITLE));
			pw.println();
			int numMetrics = model.getNumMetrics();
			pw.print(DamusBundle.getString(DamusBundle.METRICS));
			pw.print(TAB);
			pw.println(numMetrics);
			for (int i = 0; i < numMetrics; i++) {
				Metric m = model.getMetric(i);
				pw.print(m.getName());
				pw.print(TAB);
				pw.println(m.getType().name());
			}
			pw.println();
			
			int numResults = model.getNumResults();
			pw.print(DamusBundle.getString(DamusBundle.RESULTS));
			pw.print(TAB);
			pw.println(numResults);
			for (int i = 0; i < numResults; i++) {
				Metric m = model.getResult(i);
				pw.print(m.getName());
				pw.print(TAB);
				pw.println(m.getType().name());
			}
			pw.println();
			
			data = (TrainingData)data.clone();
			
			int numItems = data.getNumItems();
			pw.print(DamusBundle.getString(DamusBundle.TRAINITEM));
			pw.print(TAB);
			pw.println(numItems);
			for (int i = 0; i < numItems; i++) {
				TrainingItem item = data.getItem(i);
				pw.print(item.getDescription());
				for (int m = 0; m < numMetrics; m++) {
					pw.print(TAB);
					pw.print(item.getValue(model.getMetric(m)));
				}
				for (int r = 0; r < numResults; r++) {
					pw.print(TAB);
					pw.print(item.getValue(model.getResult(r)));
				}
				pw.println();
			}
			pw.println();
		} finally {
			Closer.close(pw);
		}
	}
	
	static class DamusFileFilter extends FileFilter {
			@Override
			public boolean accept(File cf) {
				if (cf.isDirectory()) {
					return true;
				} else {
					return cf.getName().endsWith(EXT);
				}
			}

			@Override
			public String getDescription() {
				return DamusBundle.getString(DamusBundle.DAMUS_FILE_DESCRIPTION);
			}
	}
}
