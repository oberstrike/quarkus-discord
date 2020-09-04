package de.core.domain.client

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import javax.ws.rs.*
import javax.ws.rs.core.Form
import javax.ws.rs.core.MediaType


@RegisterRestClient
interface UserAuthClient {

    @Path("/token")
    @POST
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    fun login(@RequestBody login: Form): Map<String, Any>
}
