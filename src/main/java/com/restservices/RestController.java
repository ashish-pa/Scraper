package com.restservices;

import java.net.UnknownHostException;
import java.text.ParseException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.utils.DateUtils;
import com.utils.MongoUtils;
import com.requestbeans.RequestBean;

/**
 * Main rest controller for all search endpoints
 * @author Ashish Patel
 */
@Path("/search")
public class RestController {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response search(RequestBean requestBean) throws ParseException {
		String response = MongoUtils.distinctQuery(requestBean).toString();
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Path("/sources")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getShowSourcesForDate(RequestBean requestBean) throws ParseException {
		String response = MongoUtils.getShowSourcesForDate(requestBean).toString();
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
	}
}
