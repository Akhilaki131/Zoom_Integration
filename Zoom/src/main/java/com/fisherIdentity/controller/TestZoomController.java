package com.fisherIdentity.controller;

import com.fisherIdentity.helper.MeetingDetailsHelper;
import com.fisherIdentity.zoom.PersonalDetailsResponse;
import com.fisherIdentity.zoom.TestZoomUtils;
import com.fisherIdentity.zoom.ZoomMeetingResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/testzoom")
public class TestZoomController {
    private static final Logger LOG = LogManager.getLogger(TestZoomController.class);

    @Bean
    public TestZoomUtils getTestZoom() {
        return (new TestZoomUtils("zoom"));
    }

    @PostMapping("/create-meeting/oauth/{oAuthId}")
    private ResponseEntity<Object> createMeetingWithOAuth(@RequestBody(required = false) MeetingDetailsHelper meetingDetails,
                                                 @PathVariable String oAuthId) {
        ZoomMeetingResponse meetingDetailsResponse = getTestZoom().createMeetingWithOAuth(meetingDetails, oAuthId);

        return (new ResponseEntity<>(meetingDetailsResponse, HttpStatus.CREATED));
    }

    @PostMapping("/create-meeting/access-token/{accessToken}")
    private ResponseEntity<Object> createMeetingWithAccessToken(@RequestBody(required = false) MeetingDetailsHelper meetingDetails,
                                                          @PathVariable String accessToken) {
        ZoomMeetingResponse meetingDetailsResponse = getTestZoom().createMeetingWithAccessToken(meetingDetails, accessToken);

        return (new ResponseEntity<>(meetingDetailsResponse, HttpStatus.CREATED));
    }

    @PutMapping("/update-meeting/{meetingId}/access-token/{accessToken}")
    private ResponseEntity<Object> updateMeetingWithAccessToken(@RequestBody(required = false) MeetingDetailsHelper meetingDetails,
                                                                @PathVariable String meetingId,
                                                                @PathVariable String accessToken) {
        ZoomMeetingResponse meetingDetailsResponse = getTestZoom().updateMeetingWithAccessToken(meetingDetails, meetingId, accessToken);

        return (new ResponseEntity<>(meetingDetailsResponse, HttpStatus.CREATED));
    }

    @GetMapping("/user-details/access-token/{accessToken}")
    private ResponseEntity<Object> getUserDetails(@PathVariable String accessToken) {
        PersonalDetailsResponse meetingDetailsResponse = getTestZoom().getUserDetails(accessToken);

        return (new ResponseEntity<>(meetingDetailsResponse, HttpStatus.OK));
    }

}
