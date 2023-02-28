package com.tong.notification.model.slack;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SlackThreadMessageRequestDto {
    private String key;
    private String payload;
    private String channel;
}
