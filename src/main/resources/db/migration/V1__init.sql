create table "user" (
    id          int     GENERATED ALWAYS AS IDENTITY,
    first_name  text    not null,
    last_name   text    not null,
    email       text    not null,
    age         int     not null,
    primary key (id)
);