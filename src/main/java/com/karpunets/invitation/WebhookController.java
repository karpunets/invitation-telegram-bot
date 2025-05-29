package com.karpunets.invitation;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.utility.BotUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class WebhookController {
    private final TelegramBot bot;

    @PostMapping("/")
    public void onUpdateReceived(@RequestBody String request) {
        log.info("Received update {}", request);
        Update update = BotUtils.parseUpdate(request);
        Message message = update.message();
        if (message == null) return;
        SendMessage sendMessage = new SendMessage(message.chat().id(), message.text());
        bot.execute(sendMessage);
    }
}
