package ru.pet.studychat.repository;

import org.springframework.stereotype.Repository;
import ru.pet.studychat.dto.UserRequestDto;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class UserRepository {
    /**
     * Thread safe set.
     */
    private CopyOnWriteArrayList<UserRequestDto> users = new CopyOnWriteArrayList<>();

    public CopyOnWriteArrayList<UserRequestDto> getUsers() {
        return users;
    }

    public void setUsers(CopyOnWriteArrayList<UserRequestDto> users) {
        this.users = users;
    }

    public void removeUser(UserRequestDto user) {
        users.remove(user);
    }
}
