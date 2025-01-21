create table IF NOT EXISTS "author" (
    "id" varchar primary key,
    "name" varchar not null,
    "gender" char(1) check ("gender" in ('M', 'F')) not null
 );


