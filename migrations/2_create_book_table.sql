create table IF NOT EXISTS "book" (
    "id" varchar primary key,
    "name" varchar not null,
    "author_id" varchar references "author"("id") not null,
    "pageNumbers" int not null,
    "topic" varchar(1) check ("topic" in ('ROMANCE', 'COMEDY', 'OTHER')) not null,
    "releaseDate" int check ("releaseDate" > 0) not null
);
