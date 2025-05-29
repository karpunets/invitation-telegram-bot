package com.karpunets.invitation;

import com.karpunets.invitation.event.TelegramNotifyEvent;
import com.karpunets.invitation.model.History;
import com.karpunets.invitation.model.User;
import com.karpunets.invitation.repository.HistoryRepository;
import com.karpunets.invitation.repository.UserRepository;
import com.karpunets.invitation.steps.UserStateService;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateHandler {
    private final ApplicationEventPublisher eventPublisher;
    private final UserRepository userRepository;
    private final HistoryRepository historyRepository;
    private final UserStateService stateService;


    public void handle(Update update) {
        var message = update.message();
        if (message == null) {
            eventPublisher.publishEvent(new TelegramNotifyEvent(update, "Unexpected: update message is null"));
            return;
        }
        try {
            var user = getUser(message);
            saveHistory(user, update);
            stateService.handle(user, message);
        } catch (Exception e) {
            log.error("UpdateHandler error:", e);
            eventPublisher.publishEvent(new TelegramNotifyEvent(update, e.getMessage()));
        }
    }

    private void saveHistory(User user, Update update) {
        var history = new History();
        history.setUser(user);
        history.setContent(update.toString());
        history.setCreatedAt(LocalDateTime.now());
        historyRepository.save(history);
    }

    private User getUser(Message message) {
        var telegramUser = message.from();
        return userRepository.findByTelegramId(telegramUser.id()).orElseGet(() -> {
            var newUser = new User();
            newUser.setTelegramId(telegramUser.id());
            newUser.setFirstName(telegramUser.firstName());
            newUser.setLastName(telegramUser.lastName());
            newUser.setUsername(telegramUser.username());
            newUser.setLanguageCode(telegramUser.languageCode());
            newUser.setIsBot(telegramUser.isBot());
            newUser.setCreatedAt(LocalDateTime.now());
            return userRepository.save(newUser);
        });
    }
}
