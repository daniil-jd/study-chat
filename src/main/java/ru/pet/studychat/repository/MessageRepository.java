package ru.pet.studychat.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import ru.pet.studychat.dto.MessageDto;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
@RequiredArgsConstructor
public class MessageRepository {
    private final MessageSource messageSource;

    /**
     * Thread safe list.
     */
    private CopyOnWriteArrayList<MessageDto> messages = new CopyOnWriteArrayList<>();

    public void save(MessageDto message) {
        if (message.getId() == 0) {
            boolean isUserAlreadyLogin = false;
            for (MessageDto m : messages) {
                if (m.getMessage().contains(messageSource.getMessage("chat.user.new", null, Locale.forLanguageTag("RU")))
                        && m.getMessage().equals(message.getMessage())) {
                    isUserAlreadyLogin = true;
                }
            }
            if (!isUserAlreadyLogin) {
                messages.add(message);
            }
        } else {
            for (MessageDto m : messages) {
                if (m.getId() == message.getId()) {
                    messages.remove(m);
                    messages.add(message);
                }
            }
        }
    }

    public List<MessageDto> getAllMessages() {
        return messages;
    }
}
