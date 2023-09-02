create or replace view public_view_of_schema2 as
    select * from secondary_schema.schema2_table
        where v like '%it%';
