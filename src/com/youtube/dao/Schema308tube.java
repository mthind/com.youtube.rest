package com.youtube.dao;

import java.sql.*;

import org.codehaus.jettison.json.JSONArray;

import com.youtube.util.ToJSON;


public class Schema308tube extends Oracle308tube {


	/**
	 * This method allows you to update PC_PARTS table
	 *
	 * Note: there is no validation being done... if this was a real project you
	 * must do validation here!
	 *
	 * @param pk
	 * @param avail
	 * @return
	 * @throws Exception
	 */
	public int updatePC_PARTS(int pk, int avail) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;
		try {
			/*
			 * If this was a real application, you should do data validation here
			 * before updating data.
			 */
			conn = oraclePcPartsConnection();
			query = conn.prepareStatement("update PC_PARTS " +
					"set PC_PARTS_AVAIL = ? " +
					"where PC_PARTS_PK = ? ");
			query.setInt(1, avail);
			query.setInt(2, pk);
			query.executeUpdate();

		} catch(Exception e) {
			e.printStackTrace();
			return 500;
		}
		finally {
			if (conn != null) conn.close();
		}
		return 200;
	}

	public int insertIntoPC_PARTS(String PC_PARTS_PK,
			String PC_PARTS_TITLE, 
			String PC_PARTS_CODE, 
			String PC_PARTS_MAKER,
			String PC_PARTS_AVAIL,
			String PC_PARTS_DESC) throws Exception{
		PreparedStatement query = null;
		Connection conn = null;

		try{
			conn = oraclePcPartsConnection();

			query = conn.prepareStatement("insert into PC_PARTS " +
					"(PC_PARTS_PK, PC_PARTS_TITLE, PC_PARTS_CODE, PC_PARTS_MAKER, PC_PARTS_AVAIL, PC_PARTS_DESC) " +
					"VALUES (?, ?, ?, ?, ?, ?)");

			int partPK = Integer.parseInt(PC_PARTS_PK);
			query.setInt(1, partPK);

			query.setString(2, PC_PARTS_TITLE );
			query.setString(3, PC_PARTS_CODE );
			query.setString(4, PC_PARTS_MAKER );

			int partsAvail = Integer.parseInt(PC_PARTS_AVAIL);
			query.setInt(5, partsAvail);

			query.setString(6, PC_PARTS_DESC);

			query.executeUpdate(); //note the new command for insert statement
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
	 * Method to return an item from the PC_PARTS
	 * table given an item code and brand
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


	/**
	 * This method allows you to delete a row from PC_PARTS table
	 *
	 * If you need to do a delete, consider moving the data to a archive table, then
	 * delete. Or just make the data invisible to the user. Delete data can be
	 * very dangerous.
	 *
	 * @param pk
	 * @return
	 * @throws Exception
	 */
	public int deletePC_PARTS(int pk) throws Exception {
		PreparedStatement query = null;
		Connection conn = null;
		try {
			/*
			 * If this was a real application, you should do data validation here
			 * before deleting data.
			 */
			conn = oraclePcPartsConnection();
			query = conn.prepareStatement("delete from PC_PARTS " +
					"where PC_PARTS_PK = ? ");
			query.setInt(1, pk);
			query.executeUpdate();
			
			query.close();
		} catch(Exception e) {
			e.printStackTrace();
			return 500;
		}
		finally {
			if (conn != null) conn.close();
		}
		return 200;
	}
}
