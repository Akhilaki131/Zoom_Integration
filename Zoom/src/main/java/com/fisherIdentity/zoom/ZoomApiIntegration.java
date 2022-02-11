package com.fisherIdentity.zoom;

import com.fisherIdentity.constants.MessageConstants;
import com.fisherIdentity.exception.ResourceNotFoundException;
import com.fisherIdentity.helper.MeetingDetailsHelper;
import com.fisherIdentity.helper.TokenResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Component
public class ZoomApiIntegration {
    private static final Logger LOG = LogManager.getLogger(ZoomApiIntegration.class);

    @Autowired
    private ApplicationProperties applicationProperties;

    @Bean
    public WebClient getWebClient() {
        return (WebClient.builder().build());
    }

    ZoomDetails zoomDetails = new ZoomDetails();


    // https://marketplace.zoom.us/docs/api-reference/zoom-api/meetings/meetingcreate
    public ClientResponse callCreateMeetingApi(MeetingDetailsHelper meetingDetails, ZoomMeetingRequest zoomMeetingRequest, String accessToken) {

        WebClient webClient = getWebClient();

        String authorizationToken = "Bearer " + accessToken;

        String zoomCreateMeetingUrl = zoomDetails.createMeetingUrl;
        zoomCreateMeetingUrl = zoomCreateMeetingUrl.replace("{userId}", meetingDetails.getUserId());
        ClientResponse clientResponse = webClient.post()
                .uri(zoomCreateMeetingUrl)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, authorizationToken)
                .body(Mono.just(zoomMeetingRequest), ZoomMeetingRequest.class)
                .exchange()
                .block();

        return clientResponse;
    }

    public PersonalDetailsResponse callCurrentUserApi(String accessToken) {
        WebClient webClient = getWebClient();
        String authorizationToken = "Bearer " + accessToken;

        PersonalDetailsResponse personalDetailsResponse = new PersonalDetailsResponse();
        String userZoomDetailsUrl = zoomDetails.userZoomDetailsUrl;
        ClientResponse clientResponse = webClient.get()
                .uri(userZoomDetailsUrl)
                .header(HttpHeaders.AUTHORIZATION, authorizationToken)
                .exchange()
                .block();

        Mono<PersonalDetailsResponse> personalDetailsResponseMono = clientResponse.bodyToMono(PersonalDetailsResponse.class);
        PersonalDetailsResponse newPersonalDetailsResponse = personalDetailsResponseMono.block();

        return newPersonalDetailsResponse;
    }

    public PersonalListDetailsResponse callCurrentUserApi(String accessToken) {
        WebClient webClient = getWebClient();
        String authorizationToken = "Bearer " + accessToken;

        PersonalDetailsResponse personalDetailsResponse = new PersonalDetailsResponse();
        String userZoomDetailsUrl = zoomDetails.userZoomDetailsListUrl;
        ClientResponse clientResponse = webClient.get()
                .uri(userZoomDetailsUrl)
                .header(HttpHeaders.AUTHORIZATION, authorizationToken)
                .exchange()
                .block();

        Mono<PersonalListDetailsResponse> personalListDetailsResponseMono = clientResponse.bodyToMono(PersonalListDetailsResponse.class);
        PersonalListDetailsResponse personalListDetailsResponse = personalListDetailsResponseMono.block();

        return personalListDetailsResponse;
    }

}
