package com.tong.notification.model.webhook;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WebhookMessageDto {
    String url;
    String text;
    String optionalCode;
}
