package com.youtube.rest.status;

import javax.ws.rs.*;

import javax.ws.rs.core.*;

import java.sql.*;

import com.youtube.dao.*;

@Path("/v1/status")
public class V1_status {

	private static final String version = "0.0.2.00";

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnTitle(){

		return "<p> Java Web Service </p>";
	};

	@Path("/version")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnVersion(){

		return "<p> Version : </p>" + version;
	};

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@Path("/database")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnDatabaseStatus() throws Exception{
		PreparedStatement query = null;
		String myString = null;
		String returnString = null;
		Connection conn = null;

		try {
			conn = Oracle308tube.Oracle308tubeConn().getConnection();
			query = conn.prepareStatement("select to_char(sysdate, 'YYYY-MM-DD HH24:MI:SS') DATETIME"+
					" from sys.dual");
			ResultSet resultSet = query.executeQuery();

			while(resultSet.next()){
				myString = resultSet.getString("DATETIME");
			}

			query.close(); // close it !!

			returnString = "<p> Database Status</p> " + "<p>Database Date : Time return" + myString + "</p>";
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if (conn !=null){
				query.close(); // even if there was and error, this WILL run!
			}
		}

		return returnString;
	}
}
