CREATE TABLE IF NOT EXISTS mpa (
    mpa_id int AUTO_INCREMENT PRIMARY KEY,
    name varchar
);

CREATE TABLE IF NOT EXISTS likes (
     film_id bigint,
     user_id bigint
);

CREATE TABLE IF NOT EXISTS genre (
     genre_id int AUTO_INCREMENT PRIMARY KEY,
     name varchar
);

CREATE TABLE IF NOT EXISTS film (
    film_id bigint AUTO_INCREMENT PRIMARY KEY,
    name varchar,
    description varchar,
    release_date date,
    duration bigint,
    mpa_id int,
    foreign key (mpa_id) references mpa(mpa_id)
);

CREATE TABLE IF NOT EXISTS filmorate_user (
    user_id bigint AUTO_INCREMENT PRIMARY KEY,
    login varchar,
    name varchar,
    email varchar,
    birthdate date
);

CREATE TABLE IF NOT EXISTS film_genre (
    genre_id int,
    film_id int,
    foreign key (genre_id) references genre(genre_id)
);



CREATE TABLE IF NOT EXISTS relationship_status (
    status_id int AUTO_INCREMENT PRIMARY KEY,
    name varchar
);

CREATE TABLE IF NOT EXISTS relationship (
    relation_id bigint AUTO_INCREMENT PRIMARY KEY,
    user_id bigint,
    friend_id bigint,
    status_id int,
    foreign key (user_id) references filmorate_user(user_id),
    foreign key (friend_id) references filmorate_user(user_id),
    foreign key (status_id) references relationship_status (status_id)
);



