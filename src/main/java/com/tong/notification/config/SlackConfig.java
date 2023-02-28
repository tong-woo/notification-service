package com.tong.notification.config;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlackConfig {
    @Bean
    MethodsClient slackMethodsClient(@Value("${secrets.slack.oAuth.token}") String slackOauthToken) {
        return Slack.getInstance().methods(slackOauthToken);
    }
}
