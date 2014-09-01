package com.youtube.dao;

import javax.naming.*;
import javax.sql.*;
import java.sql.Connection;




public class Oracle308tube {
	private static DataSource Oracle308tube = null;
	private static Context context = null;

	//define a public API
	/*
	 * Other methods will call this 
	 * data access APi to get 
	 * access to data in DB @ back end
	 */
	public static DataSource Oracle308tubeConn() throws Exception{
		
		/*
		 * We have the connection
		 * already.. No need to have separate 
		 * instances.
		 */
		if (Oracle308tube != null){
			return Oracle308tube;
		}
		try {
			if (context == null){
				// create a new context
				context = new InitialContext();
			}
			// connect to db using JNDI
			Oracle308tube = (DataSource) context.lookup("oracledb");
		}
		catch(Exception exc){
			exc.printStackTrace();
		}
		return Oracle308tube;
	}
	
	
	/**
	 * Split out this method.
	 * Create connection here.
	 * @return
	 */
	protected static Connection oraclePcPartsConnection() {
		
		Connection conn = null;
		try{
			conn = Oracle308tubeConn().getConnection();
			return conn;
		}
		catch(Exception exc) {
			exc.printStackTrace();
		}
		return conn;
	}
}
