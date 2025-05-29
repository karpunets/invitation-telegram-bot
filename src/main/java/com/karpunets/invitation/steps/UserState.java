package com.karpunets.invitation.steps;

import com.karpunets.invitation.model.User;
import com.pengrad.telegrambot.model.Message;

public interface UserState {

    String id();

    String interact(User user, Message message);
}
