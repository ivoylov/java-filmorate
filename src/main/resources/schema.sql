CREATE TABLE IF NOT EXISTS film (
    film_id bigint AUTO_INCREMENT PRIMARY KEY,
    name varchar,
    description varchar,
    release_date date,
    duration bigint,
    genre_id int,
    mpa_id int
);

CREATE TABLE IF NOT EXISTS filmorate_user (
    user_id bigint AUTO_INCREMENT PRIMARY KEY,
    login varchar,
    name varchar,
    email varchar,
    birthdate date
);

CREATE TABLE IF NOT EXISTS mpa (
    mpa_id int AUTO_INCREMENT PRIMARY KEY,
    name varchar
);

CREATE TABLE IF NOT EXISTS genre (
    genre_id int AUTO_INCREMENT PRIMARY KEY,
    name varchar
);

CREATE TABLE IF NOT EXISTS likes (
    film_id bigint,
    user_id bigint
);

CREATE TABLE IF NOT EXISTS relationship_status (
    status_id int AUTO_INCREMENT PRIMARY KEY,
    name varchar
);

CREATE TABLE IF NOT EXISTS relationship (
    relation_it bigint AUTO_INCREMENT PRIMARY KEY,
    user_id bigint,
    friend_id bigint,
    status_id int
);



