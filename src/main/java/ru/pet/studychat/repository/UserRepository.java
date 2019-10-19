package ru.pet.studychat.repository;

import org.springframework.stereotype.Repository;
import ru.pet.studychat.dto.UserRequestDto;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {
    /**
     * Thread safe set.
     */
    private Set<UserRequestDto> users = ConcurrentHashMap.newKeySet();

    public Set<UserRequestDto> getUsers() {
        return users;
    }

    public void setUsers(Set<UserRequestDto> users) {
        this.users = users;
    }
}
