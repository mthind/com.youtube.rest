package com.youtube.rest.status;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/v1/status")
public class V1_status {
	
	private static final String version = "0.0.1.00";
	
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
}
