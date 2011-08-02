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
package com.mebigfatguy.damus.gui.model;

import javax.swing.table.AbstractTableModel;

import com.mebigfatguy.damus.bundle.DamusBundle;
import com.mebigfatguy.damus.data.ForecastDataType;
import com.mebigfatguy.damus.data.Metric;
import com.mebigfatguy.damus.data.MetricType;
import com.mebigfatguy.damus.data.PredictionModel;
import com.mebigfatguy.damus.main.Context;

public class MetricsTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 6612588567664333743L;

    private final PredictionModel model = Context.instance().getPredictionModel();
    private final ForecastDataType dataType;

    public MetricsTableModel(ForecastDataType type) {
        dataType = type;
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public int getRowCount() {
        switch (dataType) {
            case Metric:
                return model.getNumMetrics();

            case Result:
            default:
                return model.getNumResults();
        }
    }

    public void addRow(String name, MetricType type) {
        switch (dataType) {
            case Metric: {
                model.addMetric(new Metric(name, type));
                int row = model.getNumMetrics() - 1;
                fireTableRowsInserted(row, row);
            }
            break;

            case Result: {
                model.addResult(new Metric(name, type));
                int row = model.getNumResults() - 1;
                fireTableRowsInserted(row, row);
            }
            break;
        }
    }

    public void removeMetric(int rowIndex) {
        switch (dataType) {
            case Metric:
                model.deleteMetric(rowIndex);
                this.fireTableRowsDeleted(rowIndex, rowIndex);
                break;

            case Result:
                model.deleteResult(rowIndex);
                fireTableRowsDeleted(rowIndex, rowIndex);
                break;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Metric metric = null;

        switch (dataType) {
            case Metric:
                metric = model.getMetric(rowIndex);
                break;

            case Result:
            default:
                metric = model.getResult(rowIndex);
                break;
        }

        if (columnIndex == 0) {
            return metric.getName();
        }

        return metric.getType();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) {
            if (dataType == ForecastDataType.Metric) {
                return DamusBundle.getString(DamusBundle.METRIC_NAME);
            } else {
                return DamusBundle.getString(DamusBundle.RESULT_NAME);
            }
        } else {
            if (dataType == ForecastDataType.Metric) {
                return DamusBundle.getString(DamusBundle.METRIC_VALUE);
            } else {
                return DamusBundle.getString(DamusBundle.RESULT_VALUE);
            }
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            return String.class;
        }
        return MetricType.class;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Metric metric = null;

        switch (dataType) {
            case Metric:
                metric = model.getMetric(rowIndex);
                break;

            case Result:
                metric = model.getResult(rowIndex);
                break;

            default:
                return;
        }

        if ((columnIndex == 0) && (value instanceof String)) {
            metric.setName((String)value);
        } else if ((columnIndex == 1) && (value instanceof MetricType)) {
            metric.setType((MetricType)value);
        }
        fireTableRowsUpdated(rowIndex, rowIndex);
    }


}
