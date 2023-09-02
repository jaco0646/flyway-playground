--The secondary schema needs to be generated only in H2.
--The primary schema should always be generated.

create schema if not exists secondary_schema;

create table if not exists secondary_schema.schema2_table (
    k   text    primary key,
    v   text    not null
);
