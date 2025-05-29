package com.karpunets.invitation.event;

import com.pengrad.telegrambot.model.Update;
import lombok.Data;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TelegramNotifyEvent extends ApplicationEvent {
    private final Integer updateId;
    private final String message;

    public TelegramNotifyEvent(Update update, String message) {
        super(update);
        this.updateId = update.updateId();
        this.message = message;
    }
}
