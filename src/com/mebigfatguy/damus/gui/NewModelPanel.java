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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import com.mebigfatguy.damus.bundle.DamusBundle;
import com.mebigfatguy.damus.data.ForecastDataType;
import com.mebigfatguy.damus.gui.actions.AcceptNewModelAction;
import com.mebigfatguy.damus.gui.actions.AddForecastDataTypeAction;
import com.mebigfatguy.damus.gui.actions.CancelNewModelAction;
import com.mebigfatguy.damus.gui.actions.DeleteForecastDataTypeAction;
import com.mebigfatguy.damus.gui.model.MetricsTableModel;

public class NewModelPanel extends JPanel {

    private static final long serialVersionUID = 3740341073101896416L;

    private final PanelViewer panelViewer;
    private final boolean newModel;
    private JButton addMetricButton;
    private JButton deleteMetricButton;
    private JButton addResultButton;
    private JButton deleteResultButton;

    public NewModelPanel(PanelViewer viewer, boolean showNewModel) {
        panelViewer = viewer;
        newModel = showNewModel;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(4, 4));

        add(createModelPanel(), BorderLayout.CENTER);

        if (newModel) {
            add(createCtrlPanel(), BorderLayout.SOUTH);
        }

        GUIUtils.sizeUniformly(addMetricButton, deleteMetricButton, addResultButton, deleteResultButton);
    }

    private JPanel createModelPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout(4, 4));

        JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        p.add(sp, BorderLayout.CENTER);

        JPanel metricsPanel = createMetricsPanel();
        JPanel resultsPanel = createResultsPanel();

        sp.add(metricsPanel);
        sp.add(resultsPanel);
        sp.setDividerLocation(300);
        return p;
    }

    private JPanel createMetricsPanel() {
        JPanel metricsPanel = new JPanel();
        metricsPanel.setLayout(new BorderLayout(4, 4));
        MetricsTableModel mtm = new MetricsTableModel(ForecastDataType.Metric);

        JTable metricTable = new JTable(mtm);
        TableColumn column = metricTable.getColumnModel().getColumn(1);
        column.setCellEditor(new MetricTypeCellEditor());
        metricsPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(DamusBundle.getString(DamusBundle.METRICS)), BorderFactory.createEmptyBorder(10, 0, 10, 0)));
        metricsPanel.add(new JScrollPane(metricTable), BorderLayout.CENTER);

        JPanel ctrlPanel = new JPanel();
        ctrlPanel.setLayout(new BoxLayout(ctrlPanel, BoxLayout.Y_AXIS));
        ctrlPanel.add(Box.createVerticalGlue());
        addMetricButton = new JButton(new AddForecastDataTypeAction(metricTable, ForecastDataType.Metric));
        ctrlPanel.add(addMetricButton);
        ctrlPanel.add(Box.createVerticalStrut(10));
        deleteMetricButton = new JButton(new DeleteForecastDataTypeAction(metricTable, ForecastDataType.Metric));
        ctrlPanel.add(deleteMetricButton);
        ctrlPanel.add(Box.createVerticalGlue());
        ctrlPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        metricsPanel.add(ctrlPanel, BorderLayout.EAST);

        return metricsPanel;
    }

    private JPanel createResultsPanel() {
        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BorderLayout(4, 4));
        MetricsTableModel dtm = new MetricsTableModel(ForecastDataType.Result);

        JTable resultTable = new JTable(dtm);
        TableColumn column = resultTable.getColumnModel().getColumn(1);
        column.setCellEditor(new MetricTypeCellEditor());
        resultsPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(DamusBundle.getString(DamusBundle.RESULTS)), BorderFactory.createEmptyBorder(10, 0, 10, 0)));
        resultsPanel.add(new JScrollPane(resultTable), BorderLayout.CENTER);

        JPanel ctrlPanel = new JPanel();
        ctrlPanel.setLayout(new BoxLayout(ctrlPanel, BoxLayout.Y_AXIS));
        ctrlPanel.add(Box.createVerticalGlue());
        addResultButton = new JButton(new AddForecastDataTypeAction(resultTable, ForecastDataType.Result));
        ctrlPanel.add(addResultButton);
        ctrlPanel.add(Box.createVerticalStrut(10));
        deleteResultButton = new JButton(new DeleteForecastDataTypeAction(resultTable, ForecastDataType.Result));
        ctrlPanel.add(deleteResultButton);
        ctrlPanel.add(Box.createVerticalGlue());
        ctrlPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        resultsPanel.add(ctrlPanel, BorderLayout.EAST);

        return resultsPanel;
    }

    private JPanel createCtrlPanel() {
        JPanel ctrlPanel = new JPanel();
        ctrlPanel.setLayout(new BoxLayout(ctrlPanel, BoxLayout.X_AXIS));

        JButton okButton = new JButton(new AcceptNewModelAction(panelViewer));
        JButton cancelButton = new JButton(new CancelNewModelAction(panelViewer));

        GUIUtils.sizeUniformly(okButton, cancelButton);
        ctrlPanel.add(Box.createHorizontalGlue());
        ctrlPanel.add(okButton);
        ctrlPanel.add(Box.createHorizontalStrut(10));
        ctrlPanel.add(cancelButton);
        ctrlPanel.add(Box.createHorizontalGlue());

        ctrlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return ctrlPanel;
    }
}
