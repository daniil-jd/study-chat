package ru.pet.studychat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pet.studychat.dto.UserRequestDto;
import ru.pet.studychat.repository.UserRepository;

import java.util.List;
import java.util.Optional;

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
        List<UserRequestDto> users = repository.getUsers();
        if (users.contains(user)) {
            return false;
        }

        return users.add(user);
    }

    /**
     * Find user in 'repository' by login.
     * @param login user login
     * @return user
     */
    public Optional<UserRequestDto> getUserByLogin(String login) {
        return repository.getUsers().stream()
                .filter(u -> u.getLogin().equals(login))
                .findFirst();
    }

    /**
     * Remove user from 'repository' by login.
     * @param login user login
     */
    public void removeUserByLogin(String login) {
        Optional<UserRequestDto> user = getUserByLogin(login);
        if (user.isPresent()) {
            repository.removeUser(user.get());
        }
    }
}
