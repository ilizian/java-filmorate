package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

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
        User userFriend = userStorage.getUserById(friendId);
        user.getFriends().add(friendId);
        userFriend.getFriends().add(id);
        return user;
    }

    public User removeUserFriend(long id, long friendId) throws NotFoundException {
        User user = userStorage.getUserById(id);
        User userFriend = userStorage.getUserById(friendId);
        user.getFriends().remove(friendId);
        userFriend.getFriends().remove(id);
        return user;
    }

    public List<User> getUserFriends(long id) throws NotFoundException {
        User user = userStorage.getUserById(id);
        ArrayList<User> friends = new ArrayList<>();
        for (Long friendId : user.getFriends()) {
            friends.add(getUserById(friendId));
        }
        return friends;
    }

    public List<User> getCommonFriends(long id1, long id2) throws NotFoundException {
        User user1 = userStorage.getUserById(id1);
        User user2 = userStorage.getUserById(id2);
        List<User> userList = new ArrayList<>();
        for (Long idFriend : user1.getFriends()) {
            if (user2.getFriends().contains(idFriend)) {
                userList.add(userStorage.getUserById(idFriend));
            }
        }
        return userList;
    }

}
