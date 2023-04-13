package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.db.dal.FriendsStorage;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    @Qualifier("userDbStorage")
    private final UserStorage userStorage;
    private final FriendsStorage friendsStorage;

    public User addUser(User user) throws ValidationException {
        return userStorage.addUser(user);
    }

    public User updateUser(User user) throws ValidationException, NotFoundException {
        return userStorage.updateUser(user);
    }

    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User getUserById(long id) throws NotFoundException {
        return userStorage.getUserById(id);
    }

    public User addUserFriend(long id, long friendId) throws NotFoundException {
        User user = userStorage.getUserById(id);
        User friend = userStorage.getUserById(friendId);
        friendsStorage.addUserFriend(id, friendId);
        user.getFriends().add(friendId);
        return null;
    }

    public User removeUserFriend(long id, long friendId) {
        friendsStorage.removeUserFriend(id, friendId);
        return null;
    }

    public List<User> getUserFriends(long id) {
        return friendsStorage.getUserFriends(id);
    }

    public List<User> getCommonFriends(long id, long otherId) {
        return friendsStorage.getCommonFriends(id, otherId);
    }
}
