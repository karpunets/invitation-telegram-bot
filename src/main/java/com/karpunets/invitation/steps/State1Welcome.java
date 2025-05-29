package com.karpunets.invitation.steps;

import com.karpunets.invitation.model.User;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class State1Welcome implements UserState {
    public static final String ID = "STEP_1_WELCOME";

    private final TelegramBot bot;

    @Override
    public String id() {
        return ID;
    }

    @Override
    public String interact(User user, Message message) {

        String text = Optional.ofNullable(message.text()).orElse("ahahaha");
        SendMessage sendMessage = new SendMessage(message.chat().id(), text);
        bot.execute(sendMessage);

        return ID;
    }
}
