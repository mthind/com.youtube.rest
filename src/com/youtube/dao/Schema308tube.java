package com.youtube.dao;

import java.sql.*;

import org.codehaus.jettison.json.JSONArray;

import com.youtube.utilities.ToJSON;


public class Schema308tube extends Oracle308tube {
	
	
	public int insertIntoPC_PARTS(String PC_PARTS_TITLE, 
								String PC_PARTS_CODE, 
								String PC_PARTS_MAKER,
								String PC_PARTS_AVAIL,
								String PC_PARTS_DESC) throws Exception{
		PreparedStatement query = null;
		Connection conn = null;
		
		try{
			conn = oraclePcPartsConnection();
			
			query = conn.prepareStatement("insert into PC_PARTS " +
					"PC_PARTS_TITLE, PC_PARTS_CODE, PC_PARTS_MAKER, PC_PARTS_AVAIL, PC_PARTS_DESC " +
					"VALUES (?, ?, ?, ?, ?)");
			
			query.setString(1, PC_PARTS_TITLE );
			query.setString(2, PC_PARTS_CODE );
			query.setString(3, PC_PARTS_MAKER );
			
			int partsAvail = Integer.parseInt(PC_PARTS_AVAIL);
			query.setInt(4, partsAvail);
			
			query.setString(3, PC_PARTS_DESC);
		}
		catch (Exception e) {
			e.printStackTrace();
			return 500;
		}
		finally{
			if(conn != null){
				conn.close();
			}
		}
		return 200;
	}
	/**
	 * 
	 * @param brand
	 * @return
	 * @throws Exception
	 */
	public JSONArray queryReturnBrandParts(String brand) throws Exception{
		
		PreparedStatement query = null;
		Connection conn = null;
		ToJSON converter = new ToJSON();
		JSONArray jsonArray = new JSONArray();

		try{
			conn = oraclePcPartsConnection();
			query = conn.prepareStatement("select PC_PARTS_PK, PC_PARTS_TITLE, PC_PARTS_CODE, PC_PARTS_MAKER, " +
					"PC_PARTS_DESC FROM PC_PARTS " +
					"WHERE UPPER(PC_PARTS_MAKER) = ? ");

			query.setString(1, brand.toUpperCase());

			ResultSet rs = query.executeQuery();

			jsonArray = converter.toJSONArray(rs);

			query.close(); // close connection
		}
		catch (SQLException sqlError) {
			sqlError.printStackTrace();
			return jsonArray;
		}
		finally{
			if(conn != null){
				conn.close();
			}
		}
		return jsonArray;
	}
	
	/**
	 * 
	 * @param brand
	 * @param item_number
	 * @return
	 * @throws Exception
	 */
	public JSONArray queryReturnBrandItemNumber(String brand, int item_number) throws Exception{
		PreparedStatement query = null;
		Connection conn = null;
		ToJSON converter = new ToJSON();
		JSONArray jsonArray = new JSONArray();
		
		try{
			conn = oraclePcPartsConnection();
			
			query = conn.prepareStatement("select PC_PARTS_PK, PC_PARTS_TITLE, PC_PARTS_CODE, PC_PARTS_MAKER, " +
					"PC_PARTS_DESC FROM PC_PARTS " +
					"WHERE UPPER(PC_PARTS_MAKER) = ? " +
					"AND PC_PARTS_CODE = ?");
			
			query.setString(1, brand.toUpperCase());
			query.setInt(2, item_number);
					
			ResultSet rs = query.executeQuery();

			jsonArray = converter.toJSONArray(rs);

			query.close(); // close connection

		}
		catch (SQLException sqlError) {
			sqlError.printStackTrace();
			return jsonArray;
		}
		
		catch (Exception e2) {
			e2.printStackTrace();
			return jsonArray;
		}
		
		finally{
			if(conn != null){
				conn.close();
			}
		}
		return jsonArray;
	}
}
