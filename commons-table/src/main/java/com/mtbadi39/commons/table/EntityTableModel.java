package com.mtbadi39.commons.table;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class EntityTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private final Class entityClass;
    private List<PropertyDescriptor> properties = new ArrayList<PropertyDescriptor>();
    private List rows;

    public EntityTableModel(Class entityClass, List rows) {
        this.entityClass = entityClass;
        if (rows != null) {
            this.rows = rows;
        } else {
            this.rows = new ArrayList();
        }
    }

    @Override
    public String getColumnName(int column) {
        return properties.get(column).getDisplayName();
    }

    @Override
    public int getColumnCount() {
        return properties.size();
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public Object getValueAt(int row, int column) {
        Object value = null;
        Object entityInstance = rows.get(row);
        if (entityInstance != null) {
            PropertyDescriptor property = properties.get(column);
            Method readMethod = property.getReadMethod();
            try {
                value = readMethod.invoke(entityInstance);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            } catch (IllegalArgumentException ex) {
                throw new RuntimeException(ex);
            } catch (InvocationTargetException ex) {
                throw new RuntimeException(ex);
            }
        }
        return value;
    }

    public Object getValueAt(int row) {
        if (row > -1 && row < rows.size()) {
            Object entityInstance = rows.get(row);
            return entityInstance;
        } else {
            return null;
        }
    }

    public int getIndexOf(Object entityInstance) {
        return rows.indexOf(entityInstance);
    }

    public void addColumn(String displayName, String propertyName) {
        try {
            PropertyDescriptor property
                    = new PropertyDescriptor(propertyName, entityClass, propertyName, null);
            property.setDisplayName(displayName);
            properties.add(property);
        } catch (IntrospectionException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void resetColumns() {
        properties = new ArrayList<PropertyDescriptor>();
    }

    @SuppressWarnings("unchecked")
    public void setRow(int row, Object entityInstance) {
        rows.remove(row);

        rows.add(row, entityInstance);
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
