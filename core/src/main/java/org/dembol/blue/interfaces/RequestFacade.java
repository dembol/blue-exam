package org.dembol.blue.interfaces;

import java.util.List;
import javax.validation.constraints.Min;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/blue")
public interface RequestFacade {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/request/{id}")
	Response getRequest(@PathParam("id") Integer requestId);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/request")
	List<RequestDTO> findRequests(@QueryParam("title") String title, @QueryParam("description") String description, @QueryParam("state") String state, @QueryParam("page") @Min(0) Integer pageNumber);

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/request")
	Integer addRequest(@HeaderParam("title") String title, @HeaderParam("description") String description);

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/request/{id}/verify")
	void setRequestVerify(@PathParam("id") Integer requestId);

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/request/{id}/accept")
	void setRequestAccept(@PathParam("id") Integer requestId);

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/request/{id}/publish")
	void setRequestPublish(@PathParam("id") Integer requestId);

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/request/{id}/reject")
	void setRequestReject(@PathParam("id") Integer requestId, @HeaderParam("reason") String reason);

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/request/{id}/delete")
	void setRequestDelete(@PathParam("id") Integer requestId, @HeaderParam("reason") String reason);

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/request/{id}/content")
	void setRequestContent(@PathParam("id") Integer requestId, @HeaderParam("title") String title, @HeaderParam("description") String description);
}
