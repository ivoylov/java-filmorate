CREATE TABLE IF NOT EXISTS film (
    film_id bigint AUTO_INCREMENT PRIMARY KEY,
    name varchar,
    description varchar,
    releaseDate date,
    duration bigint,
    genre int,
    rating int
);

CREATE TABLE IF NOT EXISTS filmorate_user (
    filmorate_user_id bigint AUTO_INCREMENT PRIMARY KEY,
    login varchar,
    name varchar,
    email varchar,
    birthdate date
);

CREATE TABLE IF NOT EXISTS rating (
    rating_id int AUTO_INCREMENT PRIMARY KEY,
    name varchar
);

CREATE TABLE IF NOT EXISTS genre (
    genre_id int AUTO_INCREMENT PRIMARY KEY,
    name varchar
);

CREATE TABLE IF NOT EXISTS film_rating (
    film_id int,
    rating_id int,
    FOREIGN KEY (film_id) REFERENCES film(film_id),
    FOREIGN KEY  (rating_id) REFERENCES rating(rating_id)
);

CREATE TABLE IF NOT EXISTS film_genre (
    film_id int,
    genre_id int,
    FOREIGN KEY (film_id) REFERENCES film(film_id),
    FOREIGN KEY (genre_id) REFERENCES genre(genre_id)
);

CREATE TABLE IF NOT EXISTS likes (
    film_id bigint,
    filmorate_user_id bigint,
    FOREIGN KEY (film_id) REFERENCES film(film_id),
    FOREIGN KEY (filmorate_user_id) REFERENCES filmorate_user(filmorate_user_id)
);

CREATE TABLE IF NOT EXISTS relationship (
    relation_it bigint AUTO_INCREMENT PRIMARY KEY,
    user1_id bigint,
    user2_id bigint,
    status int,
    FOREIGN KEY (user1_id) REFERENCES filmorate_user(filmorate_user_id),
    FOREIGN KEY (user2_id) REFERENCES filmorate_user(filmorate_user_id)
);

