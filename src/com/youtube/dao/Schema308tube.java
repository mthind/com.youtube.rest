package com.youtube.dao;

import java.sql.*;

import org.codehaus.jettison.json.JSONArray;

import com.youtube.utilities.ToJSON;


public class Schema308tube extends Oracle308tube {
	
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

			query.close();
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
}
