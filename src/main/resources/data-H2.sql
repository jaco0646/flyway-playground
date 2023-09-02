insert into user_table (first_name, last_name, email, age, hobbies) values
    ('john', 'doe', 'john.doe@gmail.com', 42, array['hobby1', 'hobby2']),
    ('jane', 'doe', 'jane.doe@gmail.com', 43, array['hobby3', 'hobby4']),
    ('foo', 'bar', 'foo.bar@gmail.com', 44, array['foo', 'bar']),
    ('bar', 'foo', 'bar.foo@gmail.com', 45, array['bar', 'foo'])
;

insert into secondary_schema.schema2_table (k, v) values
    ('foo', 'bar'),
    ('baz', 'qux'),
    ('Lorem', 'ipsum'),
    ('dolor', 'sit amet'),
    ('consectetur', 'adipiscing elit'),
    ('sed do', 'eiusmod'),
    ('tempor', 'incididunt'),
    ('ut labore', 'et dolore')
;