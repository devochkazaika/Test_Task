package org.cwt.task.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.cwt.task.model.UserAnalytic;
import org.cwt.task.service.AnalyticService;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.cwt.task.utils.DateConverter.parseDate;

@Path("/analytic")
public class AnalyticResource {
    @Inject
    private AnalyticService analyticService;

    @GET
    @Path("user/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserAnalytic getUserAnalytic(@PathParam("id") UUID id,
                                        @QueryParam("rentDate") String rentDateStr,
                                        @QueryParam("returnDate") String returnDateStr
                                        ) {
        LocalDateTime rentDate = parseDate(rentDateStr);
        LocalDateTime returnDate = parseDate(returnDateStr);

        return analyticService.userAnalytics(id, rentDate, returnDate);
    }

}
