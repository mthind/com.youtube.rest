package com.youtube.rest.inventory;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.youtube.dao.Schema308tube;

import org.codehaus.jettison.json.JSONArray;

@Path("/v2/inventory")
public class V2_inventory {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrandParts(@QueryParam ("brand") String brand) throws Exception {
		String returnString = null;
		//create json array ref			
		JSONArray jsonArray = new JSONArray();

		try{

			if(brand == null){
				return Response.status(400).entity("Please specify a brand for this search!!").build();
			}
			Schema308tube dao =  new Schema308tube();
			jsonArray = dao.queryReturnBrandParts(brand);

			returnString = jsonArray.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server unable to process your request!!").build();
		}
		return Response.ok(returnString).build();

	}

	/*@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnErrorOnBrand() throws Exception {
		return Response.status(400).entity("Please specify a brand for this search!!").build();
	}*/

	@Path("/{brand}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrand(@PathParam ("brand") String brand) throws Exception {
		String returnString = null;
		//create json array ref			
		JSONArray jsonArray = new JSONArray();

		try{


			Schema308tube dao =  new Schema308tube();
			jsonArray = dao.queryReturnBrandParts(brand);

			returnString = jsonArray.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server unable to process your request!!").build();
		}
		return Response.ok(returnString).build();

	}

	@Path("/{brand}/{item_number}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnSpecificBrandItem(@PathParam ("brand") String brand,
			@PathParam ("item_number") int item_number) throws Exception{
		String returnString = null;
		//create json array ref			
		JSONArray jsonArray = new JSONArray();

		try{


			Schema308tube dao =  new Schema308tube();
			//jsonArray = dao.queryReturnBrandParts(brand);
			jsonArray = dao.queryReturnBrandItemNumber(brand, item_number);
			returnString = jsonArray.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server unable to process your request!!").build();
		}
		return Response.ok(returnString).build();

	}
}
