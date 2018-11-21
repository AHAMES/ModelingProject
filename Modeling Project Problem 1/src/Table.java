import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class Table {
	
	//A class created to simplify the table design
	//Automates the creation of tables in a  convenient manner
	
	
	JTable table;
	DefaultTableModel model;

	public Table() {
		table = new JTable();
	}

	public Table(int rows, int columns) {
		model = new DefaultTableModel();
		model.setRowCount(rows);
		model.setColumnCount(columns);
		table = new JTable(model);

	}

	public void setTitles(String [] headers)
	{
		for(int i=0;i<this.table.getColumnCount();i++)
		{
		TableColumn column1 = this.table.getTableHeader().getColumnModel().getColumn(i);
		  
		column1.setHeaderValue(headers[i]);
		} 
	}
	public void setValue(int row, int column, String value) {
		model.setValueAt(value, row, column);
	}
	public String getCell(int row,int column)
	{
		Object x= model.getValueAt(row, column);
		return x.toString() ;
	}
}
