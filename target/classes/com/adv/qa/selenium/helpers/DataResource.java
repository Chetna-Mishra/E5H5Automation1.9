package com.adv.qa.selenium.helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * Used to obtain data from csv or xml file
 * and to supply them to Data Provider 
 *
 */
public class DataResource extends ArrayList<DataRow> {
	private static final long serialVersionUID = -2613747605125230413L;

	private static final Logger log = LoggerFactory.getLogger(DataResource.class);
	/**
	 * Obtain all data from the csv or xml file
	 * @param filePath full path of the file
	 * 
	 */
	public DataResource (String filePath) {
		initialize(filePath, false, null, false,null);
	}
	
	public DataResource (String filePath,String[] nodeId) {
		initialize(filePath, false, null, false,nodeId);
	}
	/**
	 * Obtain selected data from the csv or xml file 
	 * @param filePath
	 * @param selectedNames the value is stored if its name is present in this array
	 * 
	 */
	public DataResource (String filePath, String [] selectedNames, boolean orderAsInSelectedNames) {
		initialize(filePath, true, selectedNames, orderAsInSelectedNames, null);
	}
	
	/**
	 * Added extra nodeId which is an String array
	 */
	public DataResource (String filePath, String [] selectedNames, boolean orderAsInSelectedNames, String[] nodeID) {
		initialize(filePath, true, selectedNames, orderAsInSelectedNames, nodeID);
	}


	
	/**Get data as array of DataRow;
	 * every DataRow is an element of 1-dimensional array as used in Data Provider  
	 * @return   array[][1] of DataRow
	 */
	public DataRow [] [] getDataRows4DataProvider() {
		int numberOfRows = this.size();
		DataRow rows [][] = new  DataRow [numberOfRows][1];
		for (int i = 0; i < numberOfRows; i ++){
			rows[i][0] = this.get(i);
		}
		return rows;
	}
	
	
	/**Get data as array of DataRow;
	 * select only those data rows which contain a data unit with the given requiredName;
	 * every DataRow is an element of 1-dimensional array as used in Data Provider  
	 * @param requiredName
	 * @return   array[][1] of DataRow
	 */
	public DataRow [] [] getDataRows4DataProvider(String requiredName) {
		int numberOfRows = this.size();
		int numberOfRowsWithName = 0;
		Boolean containsRequiredName [] = new Boolean [numberOfRows];
		DataRow rows [][] = new  DataRow [numberOfRows][1];
		for (int i = 0; i < numberOfRows; i ++) {
			rows[i][0] = this.get(i);
			String value = rows[i][0].get(requiredName);
			if (value != null) {
				containsRequiredName[i] = true;
				numberOfRowsWithName++;
			}
			else{
				containsRequiredName[i] = false;
			}
		}
		if (numberOfRowsWithName == 0){
			return null;
		}
		DataRow requiredRows [][] = new  DataRow [numberOfRowsWithName][1];
		int currentRequiredRow = 0;
		for (int i = 0; i < numberOfRows; i ++) {
			if(containsRequiredName[i]) {
				requiredRows [currentRequiredRow][0] = rows[i][0];
				currentRequiredRow++;
			}
		}
		return requiredRows;
	}
	
	/**
	 * This method can be used instead of readCSV(String csvFilePath) 
	 * @return 2D array of data, without names.
	 */
	public String [] [] getDataValuesAsArrayOfArray () {
		int arraySize = this.size();
		String [] [] result = new String [arraySize] [];
		for (int i = 0; i < arraySize; i ++) {
			result [i] = this.get(i).getArrayOfValuesFromDataRow();
		};
		return result;
	}

	
	
	/**
	 * Get data as an array of DataRow
	 * @return array of DataRow 
	 */
	public DataRow [] getDataAsArrayOfDataRow () {
		int arraySize = this.size();
		DataRow rows [] = new  DataRow [arraySize];
		for (int i = 0; i < arraySize; i ++){
			rows[i] = this.get(i);
		}
		return rows;
	}
	

	
	/**
	 * Get data as an array of array of DataUnit
	 * @return
	 */
	public DataUnit [] [] getDataAsArrayOfArrayOfDataUnit () {
		int arraySize = this.size();
		DataUnit [] [] result = new DataUnit [arraySize] [];
		for (int i = 0; i < arraySize; i ++) {
			result [i] = this.get(i).getDataRowAsArray();
		};
		return result;
	}

	/**
	 * Find DataRow that contains given DataUnit 
	 * @param dataUnit
	 * @return first row with the given DataUnit
	 */
	public DataRow findRow(DataUnit dataUnit) {
		DataRow result = null;
		List <DataRow> rows = findRows(dataUnit);
		if (rows.size() > 0){
			result = rows.get(0);
		}
		return result;
	}
	
	/**
	 * Find all DataRow that contain given DataUnit 
	 * @param dataUnit
	 * @return List of DataRow with the given DataUnit
	 */
	public List <DataRow> findRows (DataUnit dataUnit) {
		 List <DataRow> result = new  ArrayList <DataRow>();
			String name = dataUnit.getName();
			String value = dataUnit.getValue();
			for (DataRow row : this) {
				List <DataUnit> units = row.findNames(name);
				for(DataUnit unit : units) {
					if(unit.getValue().equals(value) ){
						result.add(row);
						break;
					};
				};
			};
		return result;
	}
	
	/**
	 * Find DataRow that contains given dataUnit1 and dataUnit2
	 * @param dataUnit1
	 * @param dataUnit2
	 * @return first row with the given dataUnit1 and dataUnit2
	 */
	public DataRow findRow(DataUnit dataUnit1, DataUnit dataUnit2) {
		DataRow result = null;
		List <DataRow> rows = findRows(dataUnit1, dataUnit2);
		if (rows.size() > 0){
			result = rows.get(0);
		}
		return result;
	}

	
	/**
	 * Find all DataRow that contain both dataUnit1 and dataUnit2 
	 * @param dataUnit1
	 * @param dataUnit2
	 * @return List of DataRow with the given dataUnit1 and dataUnit2
	 */
	public List <DataRow> findRows (DataUnit dataUnit1, DataUnit dataUnit2) {
		 List <DataRow> result = new  ArrayList <DataRow>();
		 List <DataRow> first = findRows ( dataUnit1);
	 
			String name = dataUnit2.getName();
			String value = dataUnit2.getValue();
			for (DataRow row : first) {
				List <DataUnit> units = row.findNames(name);
				for(DataUnit unit : units) {
					if(unit.getValue().equals(value) ){
						result.add(row);
						break;
					};
				};
			};
		return result;
	}

	
	
	/**
	 * Search a row with a given DataUnit, 
	 * and then a DataUnit in this row with the given name
	 * @param dataUnit searched DataUnit
	 * @param name the name of the returned DataUnit
	 * @return DataUnit with the given name from the DataRow that contains given DataUnit
	 * null if not found
	 */
	public DataUnit search(DataUnit dataUnit , String name) {
		DataUnit result = null;
		DataRow rowWithUnit = findRow(dataUnit);
		if (rowWithUnit != null) {
			DataUnit unitWithName = rowWithUnit.findName(name);
			if(unitWithName != null){
				result = unitWithName;
			}
		};
		return result;
	}
	
	/**
	 * Using given file, create DataResource;
	 * search a row with a given DataUnit, 
	 * and then a DataUnit in this row with the given name
	 * @param pathNane full path to the xml or CSV file
	 * @param dataUnit searched DataUnit
	 * @param name the name of the returned DataUnit
	 * @return DataUnit with the given name from the DataRow that contains given DataUnit
	 * null if not found
	 */
	public static DataUnit search (String pathNane, DataUnit dataUnit , String name ) {
		DataResource dataResource = new DataResource(pathNane);
		return dataResource.search(dataUnit , name);
	}
	
	
	/**
	 * Search all rows with a given DataUnit, 
	 * and then take all DataUnits in that rows with the given name
	 * @param dataUnit searched DataUnit
	 * @param name the name of the returned DataUnit
	 * @return List of DataUnit with the given name from all DataRows that contain given DataUnit
	 * null if not found
	 */
	public List <DataUnit> searchAll (DataUnit dataUnit , String name) {
		List <DataUnit> result = new ArrayList <DataUnit> ();
		List <DataRow> rows =  findRows (dataUnit);
		for(DataRow row : rows) {
			List <DataUnit> units = row.findNames(name);
			if (units.size() > 0){
				result.addAll(units);
			}
		};
		
		return result;
	}
	
	/**
	 * Search a row with a given dataUnit1 and dataUnit2, 
	 * and then a DataUnit in this row with the given name
	 * @param dataUnit1
	 * @param dataUnit2
	 * @param name
	 * @return DataUnit with the given name from the DataRow that contains given dataUnit1 and dataUnit2
	 * null if not found
	 */
	public DataUnit search(DataUnit dataUnit1 , DataUnit dataUnit2, String name) {
		DataUnit result = null;
		DataRow rowWithUnits = findRow(dataUnit1,dataUnit2);
		if (rowWithUnits != null) {
			DataUnit unitWithName = rowWithUnits.findName(name);
			if(unitWithName != null){
				result = unitWithName;
			}
		};
		return result;
	}

	
	/**
	 * Data file type
	 */
	private enum DataFileType {
		UNKNOWN,
		XML,
		CSV
	}

	/**
	 * @param filePath
	 * @return file type; UNKNOWN if not xml or csv
	 */
	private static DataFileType getDataFileType (String filePath) {
		DataFileType type = DataFileType.UNKNOWN;
		int extensionIndex = filePath.lastIndexOf('.');
		String extension = filePath.substring(extensionIndex+1);
		if (extension.equalsIgnoreCase("xml")) {
			type = DataFileType.XML;
		}
		if (extension.equalsIgnoreCase("csv")) {
			type = DataFileType.CSV;
		}
		return type;
	}
	
	
	/**
	 * @param filePath
	 * @param useSelectedNames
	 * @param selectedNames
	 * @param nodeID
	 */
	private void initialize (String filePath, boolean useSelectedNames, String [] selectedNames, boolean orderAsInSelectedNames, String[] nodeID) {
		DataFileType fileType = getDataFileType (filePath);
		switch (fileType) {
		case XML:
			if(nodeID != null)//if we will pass nodeId i.e for specified row  it will call
				{
					for (int i = 0; i < nodeID.length; i++) {
						initializeFromXml (filePath,   selectedNames, orderAsInSelectedNames, nodeID[i]);
				}
			
					}
			else //It will execute for all rows present in xml file.
			{
				initializeFromXml (filePath, useSelectedNames,  selectedNames, orderAsInSelectedNames);
			}
			break;
	
		case UNKNOWN:
			throw new IllegalArgumentException("Unknown file type: " + filePath);
		
		};
	}
	
	/**
	 * @param filePath
	 * @param useSelectedNames
	 * @param selectedNames
	 */
	private void initializeFromXml (String filePath, boolean useSelectedNames, String [] selectedNames, boolean orderAsInSelectedNames) {
		  Document doc = null;
		  File  xmlFile = new File(filePath);
		  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		  DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
			doc = db.parse(xmlFile);
			
		} catch (Exception e) {
			log.error("File is not found for initializeFromCsv()"+e);
		};
		  
		  if (doc != null) {
			  doc.getDocumentElement().normalize();
			  Element el = doc.getDocumentElement();
			  String nodeName = el.getNodeName();
			  if (!nodeName.equals("Root")) {
				  log.error("Incorrect xml document");  
			  };
			  NodeList nodeLst =el.getChildNodes();
			  for (int i = 0; i < nodeLst.getLength(); i++) {
				  Node rowNode = nodeLst.item(i);
				  if (rowNode.getNodeType() == Node.ELEMENT_NODE) {
					  if (useSelectedNames) {
						  DataRow dataRowSelected = new DataRow(rowNode,  selectedNames, orderAsInSelectedNames);
						  this.add(dataRowSelected);
					  }
					  else
					  {
						  DataRow dataRow = new DataRow(rowNode);
						  this.add(dataRow);
					  };
				  };
			  };
		  }else{
			  log.error("Errors loading xml file");  
		  };
		  
	}

	/**
	 * This method will called only if we will pass nodeID.
	 * @param filePath
	 * @param useSelectedNames
	 * @param selectedNames
	 * @param nodeID
	 */
	private void initializeFromXml (String filePath, String [] selectedNames, boolean orderAsInSelectedNames, String nodeID) {
		  Document doc = null;
		  File  xmlFile = new File(filePath);
		  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		  DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
			doc = db.parse(xmlFile);
			
		} catch (Exception e) {
			log.error("File is not found for initializeFromCsv()" +e);
		};
		  
		  if (doc != null) {
			  Element el = doc.getElementById(nodeID);
			  
			  DataRow dataRowSelected = new DataRow(el,  selectedNames, orderAsInSelectedNames);
			  this.add(dataRowSelected);
		  } else {
			  log.error("Errors loading xml file");  
		  };
		 
			  };
}
