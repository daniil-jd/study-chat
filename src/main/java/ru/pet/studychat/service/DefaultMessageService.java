package ru.pet.studychat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pet.studychat.dto.MessageDto;
import ru.pet.studychat.repository.MessageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultMessageService {
    private final MessageRepository repository;

    public void save(MessageDto message) {
        repository.save(message);
    }

    public List<MessageDto> getAllMessages() {
        return repository.getAllMessages();
    }
}
