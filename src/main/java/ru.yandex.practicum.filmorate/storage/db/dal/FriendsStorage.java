package ru.yandex.practicum.filmorate.storage.db.dal;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendsStorage {

    void addUserFriend(long id, long friendId) throws NotFoundException;

    void removeUserFriend(long id, long friendId);

    List<User> getUserFriends(long id);

    List<User> getCommonFriends(long id, long otherId);
}
