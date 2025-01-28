do
$$
    begin
        if not exists(select from pg_type where typname = 'gender') then
            create type "gender" as enum ('MALE','FEMALE');
        end if;
end
$$;

create table if not exists  "author"
(
    "id" varchar primary key,
    "name" varchar not null,
    "gender" gender
 );


