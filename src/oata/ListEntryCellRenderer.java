package oata;

import java.awt.Color;
import java.awt.Component;
import java.io.File;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.filechooser.FileSystemView;

class ListEntry
{
   private String value;
   private Icon icon;
  
   public ListEntry(String value, Icon ico) {
      this.value = value;
      this.icon = ico;
   }
  
   public String getValue() {
      return value;
   }
  
   public Icon getIcon() {
      return icon;
   }
  
   public String toString() {
      return value;
   }
}

/** A FileListCellRenderer for a File. */

class ListEntryCellRenderer
extends JLabel implements ListCellRenderer
{
 private JLabel label;

 public Component getListCellRendererComponent(JList list, Object value,
                                               int index, boolean isSelected,
                                               boolean cellHasFocus) {
    ListEntry entry = (ListEntry) value;

    setText(value.toString());
    setIcon(entry.getIcon());
 
    if (isSelected) {
       setBackground(list.getSelectionBackground());
       setForeground(list.getSelectionForeground());
    }
    else {
       setBackground(list.getBackground());
       setForeground(list.getForeground());
    }

    setEnabled(list.isEnabled());
    setFont(list.getFont());
    setOpaque(true);

    return this;
 }
}

 
