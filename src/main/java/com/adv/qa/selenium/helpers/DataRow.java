/**
 * 
 */
package com.adv.qa.selenium.helpers;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Used to store a row of data obtained from CSV or XML file
 *
 */
public class DataRow extends ArrayList<DataUnit> {
	private static final long serialVersionUID = -1418005936262692993L;


	/**
	 * @param rowNode row node from XML file
	 */
	public DataRow (Node rowNode)  {
		initialize( rowNode, false, null, false);
	}

	/**
	 * @param rowNode row node from XML file
	 * @param selectedNames Add only data units with the names from this array
	 * @param useSelectedNamesOrder  if true, store data in the order as in selectedNames
	 */
	public DataRow (Node rowNode, String [] selectedNames, boolean useSelectedNamesOrder) {
		initialize( rowNode, true, selectedNames, useSelectedNamesOrder);
	}



	/**
	 * .
	 * @param headers Array of data headers or column names (first line in csv file) 
	 * @param dataRowArray Array of data (the second, third etc. lines in csv file)
	 */
	public DataRow (String [] headers, String [] dataRowArray) {
		initialize ( headers,  dataRowArray, false, null, false);
	}




	/**
	 * @param headers Array of data headers or column names (first line in csv file) 
	 * @param dataRowArray  Array of data (the second, third etc. lines in csv file)
	 * @param selectedNames Only data from the columns which names are in this array will be used 
	 * @param useSelectedNamesOrder  if true, store data in the order as in selectedNames
	 */
	public DataRow (String [] headers, String [] dataRowArray, String [] selectedNames, boolean useSelectedNamesOrder) {
		initialize ( headers,  dataRowArray, true, selectedNames, useSelectedNamesOrder);
	}



	/**
	 * @return array of DataUnit
	 */
	public DataUnit [] getDataRowAsArray() {
		int arrayLength = this.size();
		DataUnit [] dataUnitArray = new DataUnit [arrayLength];
		for (int i = 0; i < arrayLength; i++) {
			dataUnitArray[i] = this.get(i);
		}
		return dataUnitArray;
	}

	/**
	 * @return values of the data in the given row
	 */
	public String [] getArrayOfValuesFromDataRow () {
		int arrayLength = this.size();
		String [] valueArray = new String [arrayLength];
		for (int i = 0; i < arrayLength; i++) {
			valueArray[i] = this.get(i).getValue();
		}
		return valueArray;
	}

	/**
	 * Find a DataUnit in the current DataRow with given name
	 * @param name
	 * @return  first found DataUnit with given name; 
	 * null if not found 
	 */
	public DataUnit findName(String name) {
		DataUnit result = null;
		for (DataUnit unit : this) {
			if (unit.getName() != null ){
				if(unit.getName().equals(name)){
					result = unit;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * Find the first DataUnit in the current DataRow with given name and get its value
	 * @param name
	 * @return value of  the found DataUnit; null if not found
	 */
	public String findNameGetValue(String name) {
		String value = null;
		DataUnit result = findName(name);
		if(result != null) {
			value = result.getValue();
		}
		return value;
	}

	/**
	 * Shortcut for the method findNameGetValue(String name) - it is used very often
	 * Find the first DataUnit in the current DataRow with given name and get its value
	 * @param name
	 * @return value of  the found DataUnit; null if not found
	 */
	public String get(String name) {
		return findNameGetValue(name);
	}

	/**
	 * Find all DataUnit in the current DataRow with the given name
	 * @param name
	 * @return List of all DataUnit with the given name;
	 * empty List if not found 
	 */
	public List <DataUnit> findNames (String name) {
		List <DataUnit> result = new ArrayList <DataUnit>();
		for (DataUnit unit : this) {
			if (unit.getName() != null ){
				if(unit.getName().equals(name)) {
					result.add(unit);
				}
			}
		}
		return result;
	}

	/** 
	 * Find all DataUnit in the current DataRow with the given name,
	 * and return their values
	 * @param name 
	 * @return values of the found DataUnits 
	 */
	public List <String> findNamesReturnValues(String name) {
		List <String> result = new ArrayList <String>();
		List <DataUnit> unitsWithName =  findNames (name);
		for (DataUnit unit : unitsWithName) {
			result.add(unit.getValue());
		}
		return result;
	}

	/**
	 * Find a DataUnit in the current DataRow with given value
	 * @param value
	 * @return  first found DataUnit with given value; 
	 * null if not found 
	 */
	public DataUnit findValue(String value) {
		DataUnit result = null;
		for (DataUnit unit : this) {
			if (unit.getValue() != null ){
				if(unit.getValue().equals(value)) {
					result = unit;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * Find all DataUnit in the current DataRow with the given value
	 * @param value
	 * @return List of all DataUnit with the given value;
	 * empty List if not found 
	 */
	public List <DataUnit> findValues(String value) {
		List <DataUnit> result = new ArrayList <DataUnit>();
		for (DataUnit unit : this) {
			if (unit.getValue() != null )
			{
				if(unit.getValue().equals(value)) {
					result.add(unit);
				}
			}
		}
		return result;
	}



	/**
	 * Check if the name of the DataUnit is in the array of selected names
	 * @param dataUnit
	 * @param selectedNames
	 * @return 
	 */
	private boolean isDataUnitNameInSelectedArray (DataUnit dataUnit, String [] selectedNames) {
		boolean isInArray = false;
		String name = dataUnit.getName();
		for (int i = 0; i < selectedNames.length; i ++) {
			if (selectedNames[i].equals(name)) {
				isInArray = true;
				break;
			}
		}
		return isInArray;
	}


	private List <DataUnit> getAllRowDataUnits (Node rowNode) {
		List <DataUnit> rowDataUnits = new ArrayList <DataUnit> ();

		if (rowNode.getNodeType() == Node.ELEMENT_NODE) {
			String rowNodeName = rowNode.getNodeName();
			if (rowNodeName.equals("Row")) {
				NodeList rowsNodeList = rowNode.getChildNodes();
				for (int j = 0; j < rowsNodeList.getLength(); j ++) {

					Node dataNode =  rowsNodeList.item(j);
					if (dataNode.getNodeType() == Node.ELEMENT_NODE) {
						Element dataElement = (Element) dataNode;
						DataUnit dataUnit = new DataUnit(dataElement);
						rowDataUnits.add(dataUnit);

					}
				}
			}
			else {
				throw new IllegalArgumentException("The argument is not a Row element");
			}
		} 
		else {
			throw new IllegalArgumentException("The argument is not an Element Node");
		}

		return rowDataUnits;
	}

	private void getSelectedDataUnits(List <DataUnit> allDataUnits, String [] selectedNames, boolean sortAsInArray) {
		if (sortAsInArray) {
			for (int i =0; i< selectedNames.length; i++){
				for (DataUnit dataUnit1 : allDataUnits) {
					if (dataUnit1.getName().equals(selectedNames[i])){
						this.add(dataUnit1);
					}
				}
			}
		}
		else {
			for (DataUnit dataUnit1 : allDataUnits) {
				if (isDataUnitNameInSelectedArray (dataUnit1, selectedNames)) {
					this.add(dataUnit1);
				}
			}
		}
	}

	/**
	 * Used in constructors 
	 * @param rowNode
	 * @param selectedNames
	 * @param useSelectedNames 
	 */
	private void initialize(Node rowNode, boolean useSelectedNames, String [] selectedNames, boolean sortAsInArray) {
		List <DataUnit> allDataUnits = getAllRowDataUnits (rowNode);
		if (useSelectedNames) {
			getSelectedDataUnits( allDataUnits, selectedNames,  sortAsInArray);
		}
		else{
			this.addAll(allDataUnits);
		}
	}


	/**
	 * Initialize DataRow using csv-file information
	 * 
	 * @param headers Column names in csv file
	 * @param dataRowArray a line in csv file
	 * @param useSelectedNames
	 * @param selectedNames only selected columns will be added
	 */
	private void initialize (String [] headers, String [] dataRowArray, boolean useSelectedNames, String selectedNames[], boolean useSelectedNamesOrder) {
		List <DataUnit> allDataUnits =  getAllRowDataUnits (headers, dataRowArray);
		if (useSelectedNames) {
			getSelectedDataUnits( allDataUnits, selectedNames,  useSelectedNamesOrder);
		}
		else{
			this.addAll(allDataUnits);
		}
	}


	private  List <DataUnit> getAllRowDataUnits (String [] headers, String [] dataRowArray) {
		List <DataUnit> rowDataUnits = new ArrayList <DataUnit> ();
		for (int column = 0; column < headers.length; column++ ) {
			DataUnit dataUnit = new DataUnit(headers[column], dataRowArray[column]);
			rowDataUnits.add(dataUnit); // add all data
		}

		return rowDataUnits;
	}
}
