package com.ssafy.boardcollie.global.mattermost;

import com.google.gson.Gson;
import com.ssafy.boardcollie.global.mattermost.MatterMostMessageDto.Attachment;
import com.ssafy.boardcollie.global.mattermost.MatterMostMessageDto.Attachments;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class MatterMostSender {

    private Logger log = LoggerFactory.getLogger(MatterMostSender.class);

    @Value("${notification.mattermost.enabled}")
    private boolean mmEnabled;
    @Value("${notification.mattermost.webhook-url}")
    private String webhookUrl;

    private final RestTemplate restTemplate;
    private final MatterMostProperties matterMostProperties;

    public void sendMessage(Exception e, String uri, String params) {
        if (!mmEnabled) {
            return;
        }

        try {

            Attachment attachment = Attachment.builder()
                    .channel(matterMostProperties.getChannel())
                    .authorIcon(matterMostProperties.getAuthorIcon())
                    .authorName(matterMostProperties.getAuthorName())
                    .color(matterMostProperties.getColor())
                    .footer(matterMostProperties.getFooter())
                    .pretext(matterMostProperties.getPretext())
                    .text(matterMostProperties.getText())
                    .title(matterMostProperties.getTitle())
                    .build();
            attachment.addExceptionInfo(e, uri, params);
            Attachments attachments = new Attachments(attachment);
            attachments.addProps(e);
            String payload = new Gson().toJson(attachments);

            HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<String> entity = new HttpEntity<>(payload, headers);
            restTemplate.postForEntity(webhookUrl, entity, String.class);
        } catch (Exception exception) {
            log.error("#### ERROR!! Notification Manager : {}", exception.getMessage());
        }

    }

}
