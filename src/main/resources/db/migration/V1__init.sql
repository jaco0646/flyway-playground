create table user_table (
    id          int         GENERATED ALWAYS AS IDENTITY,
    first_name  text        not null,
    last_name   text        not null,
    email       text        not null,
    age         int         not null,
    hobbies     text array  not null,
    primary key (id),
    unique (email)
);