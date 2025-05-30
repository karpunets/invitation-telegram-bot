package com.karpunets.invitation;

import com.karpunets.invitation.event.TelegramNotifyEvent;
import com.karpunets.invitation.properties.TelegramProperties;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.DeleteWebhook;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetWebhook;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InvitationTelegramBot extends TelegramBot {
    private final TelegramProperties properties;

    public InvitationTelegramBot(TelegramProperties properties) {
        super(properties.getToken());
        this.properties = properties;
    }

    @PostConstruct
    public void registerWebhook() {
        var request = new SetWebhook().url(properties.getWebhook());
        var response = execute(request);
        if (response.isOk()) {
            log.info("✅ Webhook set successfully");
        } else {
            log.error("❌ Failed to set webhook: {}", response.description());
            throw new RuntimeException("Failed to set webhook");
        }
    }

    @PreDestroy
    public void removeWebhook() {
        if (!properties.isRemoveWebhook()) {
            return;
        }
        var response = execute(new DeleteWebhook());
        if (response.isOk()) {
            log.info("❎ Webhook removed");
        } else {
            log.error("❌ Failed to remove webhook: {}", response.description());
            throw new RuntimeException("Failed to remove webhook");
        }
    }

    @EventListener
    public void telegramNotifyEvent(TelegramNotifyEvent event) {
        var message = """
                📢 %s
                %s
                
                %s
                """.formatted(
                properties.getBot(),
                event.getUpdateId(),
                event.getMessage()
        );
        var sendMessage = new SendMessage(properties.getNotifyUserId(), message);
        var response = execute(sendMessage);
        if (!response.isOk()) {
            log.error("📢 Failed to notify: {}", response.description());
        }
    }
}
