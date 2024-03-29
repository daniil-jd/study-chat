package ru.pet.studychat.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.pet.studychat.dto.MessageDto;
import ru.pet.studychat.service.DefaultMessageService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class DefaultWebSocketHandler implements WebSocketHandler {
    private ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final DefaultMessageService messageService;
    private final ObjectMapper mapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        sessions.put(webSocketSession.getId(), webSocketSession);
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        if (webSocketMessage instanceof TextMessage) {
            MessageDto messageFromSocket = mapper.readValue(((TextMessage) webSocketMessage).getPayload(), MessageDto.class);

            messageService.save(messageFromSocket);

            List<MessageDto> messages = messageService.getAllMessages();
            for (Map.Entry<String, WebSocketSession> sessionEntry : sessions.entrySet()) {
                sessionEntry.getValue().sendMessage(new TextMessage(mapper.writeValueAsString(messages)));
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        if (closeStatus.equals(CloseStatus.SERVER_ERROR)) {
//            throw new WebSocketException(closeStatus.getReason());
        }

        sessions.remove(webSocketSession.getId());
        webSocketSession.close(closeStatus);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
