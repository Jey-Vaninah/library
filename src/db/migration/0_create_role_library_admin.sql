DO
$create_role_book$
BEGIN
        IF EXISTS (
            SELECT FROM pg_catalog.pg_roles WHERE  rolname = 'book') THEN
            RAISE NOTICE 'Role "book" already exists. Skipping.';
ELSE
CREATE ROLE book LOGIN PASSWORD '123456';
END IF;
END
$create_role_book$;

GRANT ALL PRIVILEGES ON DATABASE management TO book;
ALTER DEFAULT PRIVILEGES FOR ROLE book
    GRANT ALL PRIVILEGES ON TABLES TO book;
ALTER DEFAULT PRIVILEGES FOR ROLE book
    GRANT ALL PRIVILEGES ON SEQUENCES TO book;
GRANT ALL PRIVILEGES ON SCHEMA public TO book;
\c management book

