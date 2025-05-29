package com.karpunets.invitation.steps;

import com.karpunets.invitation.model.User;
import com.karpunets.invitation.repository.UserRepository;
import com.pengrad.telegrambot.model.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserStateService {
    private final Map<String, UserState> stateMap;
    private final UserRepository userRepository;

    public UserStateService(List<UserState> userStates, UserRepository userRepository) {
        this.stateMap = userStates.stream().collect(Collectors.toMap(UserState::id, Function.identity()));
        this.userRepository = userRepository;
    }

    public void handle(User user, Message message) {
        var currentState = stateMap.getOrDefault(user.getState(), stateMap.get(State1Welcome.ID));
        var nextState = currentState.interact(user, message);
        user.setState(nextState);
        userRepository.save(user);
    }
}
