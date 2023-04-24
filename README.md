# Filmorate project.


![ER](https://user-images.githubusercontent.com/114443883/232164584-20a260c9-a20a-4d75-bf4c-c61e1539289a.png)


https://dbdiagram.io/d/643190098615191cfa8c5c63


Таблицы:
1. films (фильмы)
2. genres (жанры)
3. film_genre (фильм-жанр)
4. mpas (возрастной рейтинг)
5. likes (пользовательские лайки)
6. users (пользователи)
7. friends (друзья)


## ***Примеры запросов***

Вывод всех пользователей:
```
SELECT *
FROM users;
```

Вывод всех фильмов:
```
SELECT *
FROM films;
```
Вывод всех жанров:
```
SELECT *
FROM genre;
```
Вывод пользователя c идентификатором 999:
```
SELECT *
FROM users
WHERE user_ID = 999;
```
Вывод фильма c идентификатором 999:
```
SELECT *
FROM films
WHERE film_id = 999;
```
Получение списка жанров фильма:
```
SELECT f.name as film_name,
       g.name as film_genre
FROM films AS f
JOIN film_genre as fg ON f.film_id = fg.film_id
JOIN genre as g ON fg.genre_id = g.genre_id;
```
