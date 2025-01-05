drop table if exists users cascade;
drop table if exists user_friends cascade;
drop table if exists genres cascade;
drop table if exists mpa cascade;
drop table if exists films cascade;
drop table if exists likes cascade;
drop table if exists films_genres cascade;

create table users (
                       id serial primary key,
                       email varchar not null unique,
                       login varchar(50) not null,
                       name varchar not null,
                       birthday date
);

create table user_friends (
                              user_id int references users (id) not null,
                              friend_id int references users (id) not null,
                              primary key (user_id, friend_id)
);

create table genres (
                        id serial primary key,
                        name varchar not null
);

create table mpa (
                     id serial primary key,
                     name varchar,
                     description varchar
);

create table films (
                       id serial primary key,
                       name varchar(50) not null,
                       description varchar(200),
                       release_date date,
                       duration int not null,
                       mpa_id int references mpa (id) not null
);

create table likes (
                       user_id int references users (id) not null,
                       film_id int references films (id) not null,
                       primary key (user_id, film_id)
);

create table films_genres (
                              film_id int references films (id) not null,
                              genre_id int references genres (id) not null,
                              primary key (film_id, genre_id)
);