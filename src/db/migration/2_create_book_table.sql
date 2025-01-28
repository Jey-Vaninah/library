do
$$
    begin
        if not exists(select from pg_type where typname = 'topic') then
            create type "topic" as enum ('ROMANCE','COMEDY', 'OTHER');
        end if;
end
$$;

create table if not exists "book" (
    "id" varchar primary key,
    "name" varchar not null,
    "author_id" varchar references "author"("id") not null,
    "page_numbers" int not null,
    "topic" topic,
    "release_date" date not null
);
