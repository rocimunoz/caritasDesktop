package com.reparadoras.caritas.ui.components.combobox;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ComboBoxRenderer  extends JLabel implements ListCellRenderer
{
    private String _title;

    public ComboBoxRenderer(String title)
    {
        _title = title;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean hasFocus)
    {
        if (index == -1 && value == null) setText(_title);
        else setText(value.toString());
        return this;
    }
} 
