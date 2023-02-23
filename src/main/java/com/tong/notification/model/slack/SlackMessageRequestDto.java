package com.tong.notification.model.slack;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SlackMessageRequestDto {
    @JsonProperty("channel")
    private String channel;
    @JsonProperty("text")
    private String text;
    @JsonProperty("thread_ts")
    private String threadID;
}
