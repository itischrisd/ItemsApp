CREATE USER items_user WITH PASSWORD 'items_password';
ALTER DATABASE items OWNER TO items_user;
GRANT ALL PRIVILEGES ON DATABASE items TO items_user;
