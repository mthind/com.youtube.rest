package com.youtube.util;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.owasp.esapi.ESAPI;

import java.sql.ResultSetMetaData;

import java.sql.ResultSet;
import java.sql.Types;
/*
 * Send the data set from 
 * inventory class to this class. 
 * This will be doing the
 * parsing for us and returning the 
 * JSON data.
 */
public class ToJSON {

	public JSONArray toJSONArray(ResultSet resultSet) throws Exception{
		JSONArray jsonArray = new JSONArray(); // JSON array that will be returned

		//save the table data(column names, column count)
		ResultSetMetaData rsmd = resultSet.getMetaData();

		// loop through EACH ROW
		while (resultSet.next()){

			// figure out the columns count
			int numColumns = rsmd.getColumnCount();

			String temp = null;
			//convert each row to a JSON object			
			JSONObject obj = new JSONObject();

			// We loop through EACH COLUMN 
			// and place data in object
			for(int i = 1; i <=numColumns; i++){
				String column_name = rsmd.getColumnName(i);

				if(rsmd.getColumnType(i) == Types.ARRAY){
					obj.put(column_name, resultSet.getArray(column_name));
					/*Debug */ System.out.println("ToJSON : Array");
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.BIGINT){
					obj.put(column_name, resultSet.getInt(column_name));
					/*Debug*/ System.out.println("ToJson: BIGINT");
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.BOOLEAN){
					obj.put(column_name, resultSet.getBoolean(column_name));
					/*Debug*/ System.out.println("ToJson: BOOLEAN");
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.BLOB){
					obj.put(column_name, resultSet.getBlob(column_name));
					/*Debug*/ System.out.println("ToJson: BLOB");
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.DOUBLE){
					obj.put(column_name, resultSet.getDouble(column_name));
					/*Debug*/ System.out.println("ToJson: DOUBLE");
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.FLOAT){
					obj.put(column_name, resultSet.getFloat(column_name));
					/*Debug*/ System.out.println("ToJson: FLOAT");
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.INTEGER){
					obj.put(column_name, resultSet.getInt(column_name));
					/*Debug*/ System.out.println("ToJson: INTEGER");
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.NVARCHAR){
					obj.put(column_name, resultSet.getNString(column_name));
					/*Debug*/ System.out.println("ToJson: NVARCHAR");
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.VARCHAR){
					temp = resultSet.getString(column_name); //saving column data to temp variable
					temp = ESAPI.encoder().canonicalize(temp); //decoding data to base state
					temp = ESAPI.encoder().encodeForHTML(temp); //encoding to be browser safe
					obj.put(column_name, temp); //putting data into JSON object
					//obj.put(column_name, rs.getString(column_name));
					// /*Debug*/ System.out.println("ToJson: VARCHAR");
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.TINYINT){
					obj.put(column_name, resultSet.getInt(column_name));
					/*Debug*/ System.out.println("ToJson: TINYINT");
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.SMALLINT){
					obj.put(column_name, resultSet.getInt(column_name));
					/*Debug*/ System.out.println("ToJson: SMALLINT");
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.DATE){
					obj.put(column_name, resultSet.getDate(column_name));
					/*Debug*/ System.out.println("ToJson: DATE");
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.TIMESTAMP){
					obj.put(column_name, resultSet.getTimestamp(column_name));
					/*Debug*/ System.out.println("ToJson: TIMESTAMP");
				}
				else if(rsmd.getColumnType(i)==java.sql.Types.NUMERIC){
					obj.put(column_name, resultSet.getBigDecimal(column_name));
					// /*Debug*/ System.out.println("ToJson: NUMERIC");
				}
				else {
					obj.put(column_name, resultSet.getObject(column_name));
					/*Debug*/ System.out.println("ToJson: Object "+column_name);
				} 
			}

			// add the object to array
			jsonArray.put(obj);
		}
		return jsonArray;

	}
}
