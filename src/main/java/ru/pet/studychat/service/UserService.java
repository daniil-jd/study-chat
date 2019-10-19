package ru.pet.studychat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pet.studychat.repository.UserRepository;
import ru.pet.studychat.dto.UserRequestDto;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    /**
     * Add new user to 'repository'.
     * @param user user candidate
     * @return true - if added successfully, false - if user with login already exist
     */
    public boolean add(UserRequestDto user) {
        Set<UserRequestDto> users = repository.getUsers();
        if (users.contains(user)) {
            return false;
        }

        return users.add(user);
    }
}
