package com.youtube.rest.inventory;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;

@Path("/v2/inventory")
public class V2_inventory {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrandParts(@QueryParam ("brand") String brand) throws Exception {
		String returnString = null;
		//create json array ref			
		JSONArray jsonArray = new JSONArray();
		
		
		return null;
		
	}
		
}
