package com.youtube.rest.inventory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.QueryParam;

import org.codehaus.jettison.json.JSONArray;

import com.sun.istack.internal.Builder;
import com.youtube.dao.Oracle308tube;
import com.youtube.util.ToJSON;

@Path("/v1/inventory")
public class V1_inventory {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnAllPcParts() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		// response to be sent back to client
		Response response = null;
		try {
			conn = Oracle308tube.Oracle308tubeConn().getConnection();
			query = conn.prepareStatement("select * " +
											"from PC_PARTS");
			ResultSet resultSet = query.executeQuery();
			
			// get an instance of the converter
			ToJSON converter = new ToJSON();
			
			//create json array ref			
			JSONArray jsonArray = new JSONArray();
			
			// parse the resultSet
			jsonArray = converter.toJSONArray(resultSet);
			
			query.close(); // close the connection!!
			
			returnString = jsonArray.toString();
			
			response = Response.ok(returnString).build(); // build the Response
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(conn != null){
				conn.close();
			}
		}
		return response;
	}
}
