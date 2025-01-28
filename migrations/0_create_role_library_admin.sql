DO
$create_role_library_role$
BEGIN
        IF EXISTS (
            SELECT FROM pg_catalog.pg_roles WHERE  rolname = 'library_role') THEN
            RAISE NOTICE 'Role "library_role" already exists. Skipping.';
ELSE
CREATE ROLE library_role LOGIN PASSWORD '123456';
END IF;
END
$create_role_library_role$;

GRANT ALL PRIVILEGES ON DATABASE library TO library_role;
ALTER DEFAULT PRIVILEGES FOR ROLE library_role
    GRANT ALL PRIVILEGES ON TABLES TO library_role;
ALTER DEFAULT PRIVILEGES FOR ROLE library_role
    GRANT ALL PRIVILEGES ON SEQUENCES TO library_role;
GRANT ALL PRIVILEGES ON SCHEMA public TO library_role;
\c library library_role

