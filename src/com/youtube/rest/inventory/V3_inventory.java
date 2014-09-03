package com.youtube.rest.inventory;

import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.youtube.dao.Schema308tube;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;


@Path("/v3/inventory")
public class V3_inventory {

	/**
	 * This method will allow you
	 * to add data to the PC_PARTS table
	 * using JSON object.
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})	
	public Response addPCParts2(String incomingData) throws Exception{

		String returnString = null;
		//create json array ref			
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		Schema308tube dao =  new Schema308tube();
		try{
			JSONObject partsData = new JSONObject(incomingData);

			System.out.println("jsonData :" + partsData.toString());

			//ObjectMapper objectMapper = new ObjectMapper();
			//ItemEntry itemEntry = objectMapper.readValue(incomingData, ItemEntry.class);

			int http_code = dao.insertIntoPC_PARTS(partsData.optString("PC_PARTS_PK"),
					partsData.optString("PC_PARTS_TITLE"),
					partsData.optString("PC_PARTS_CODE"), 
					partsData.optString("PC_PARTS_MAKER"),
					partsData.optString("PC_PARTS_AVAIL"), 
					partsData.optString("PC_PARTS_DESC"));

			if(http_code == 200){
				jsonObject.put("HTTP_CODE", 200);
				jsonObject.put("MSG : ","Item has been entered successfully, Version 2");

				returnString = jsonArray.put(jsonObject).toString();

				System.out.println("returnString :" + returnString.toString());

			}
			else{
				return Response.status(500).entity("Unable to process item!!").build();
			}


		}
		catch(Exception exc){
			exc.printStackTrace();
			return Response.status(500).entity("Server unable to process your request!!").build();
		}
		return Response.ok(returnString).build();
	}

	/**
	 * This method will allow you to update data in the PC_PARTS table.
	 * In this example we are using both PathParms and the message body (payload).
	 *
	 * @param brand
	 * @param item_number
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */
	@Path("/{brand}/{item_number}")
	@PUT
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateItem(@PathParam("brand") String brand,
			@PathParam("item_number") int item_number,
			String incomingData)
					throws Exception {
		//System.out.println("incomingData: " + incomingData);
		//System.out.println("brand: " + brand);
		//System.out.println("item_number: " + item_number);
		int pk;
		int avail;
		int http_code;
		String returnString = null;
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		Schema308tube dao = new Schema308tube();
		try {
			JSONObject partsData = new JSONObject(incomingData); //we are using json objects to parse data
			pk = partsData.optInt("PC_PARTS_PK", 0);
			avail = partsData.optInt("PC_PARTS_AVAIL", 0);
			//call the correct sql method
			http_code = dao.updatePC_PARTS(pk, avail);
			if(http_code == 200) {
				jsonObject.put("HTTP_CODE", "200");
				jsonObject.put("MSG", "Item has been updated successfully");
			} else {
				return Response.status(500).entity("Server was not able to process your request").build();
			}
			returnString = jsonArray.put(jsonObject).toString();
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		return Response.ok(returnString).build();
	}


	/**
	 * This method will allow you to delete data in the PC_PARTS table.
	 *
	 * We really only need the primary key from the message body but I kept
	 * the same URL path as the update (PUT) to let you see that we can use the same
	 * URL path for each http method (GET, POST, PUT, DELETE, HEAD)
	 *
	 * @param brand
	 * @param item_number
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */
	@Path("/{brand}/{item_number}")
	@DELETE
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteItem(@PathParam("brand") String brand,
			@PathParam("item_number") int item_number,
			String incomingData)
					throws Exception {
		//System.out.println("incomingData: " + incomingData);
		//System.out.println("brand: " + brand);
		//System.out.println("item_number: " + item_number);
		int pk;
		int http_code;
		String returnString = null;
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		Schema308tube dao = new Schema308tube();
		try {
			JSONObject partsData = new JSONObject(incomingData);
			pk = partsData.optInt("PC_PARTS_PK", 0);
			http_code = dao.deletePC_PARTS(pk);
			if(http_code == 200) {
				jsonObject.put("HTTP_CODE", "200");
				jsonObject.put("MSG", "Item has been deleted successfully");
			} else {
				return Response.status(500).entity("Server was not able to process your request").build();
			}
			returnString = jsonArray.put(jsonObject).toString();
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		return Response.ok(returnString).build();
	}
}
