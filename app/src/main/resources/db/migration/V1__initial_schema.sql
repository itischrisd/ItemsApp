CREATE TABLE author
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY,
    name       VARCHAR(100) NOT NULL,
    surname    VARCHAR(100),
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    version    INTEGER,
    CONSTRAINT pk_author PRIMARY KEY (id)
);

CREATE TABLE book
(
    id                  BIGINT GENERATED ALWAYS AS IDENTITY,
    title               VARCHAR(255) NOT NULL,
    year_of_publication SMALLINT,
    cover_type          VARCHAR(100),
    serial_number       VARCHAR(20),
    edition_number      INTEGER,
    catalog_number      VARCHAR(20),
    note                VARCHAR(500),
    publisher_id        BIGINT,
    storage_id          BIGINT,
    created_at          TIMESTAMP WITHOUT TIME ZONE,
    updated_at          TIMESTAMP WITHOUT TIME ZONE,
    version             INTEGER,
    CONSTRAINT pk_book PRIMARY KEY (id)
);

CREATE TABLE book_author
(
    authors_id BIGINT,
    books_id   BIGINT,
    CONSTRAINT pk_bookauthor PRIMARY KEY (authors_id, books_id)
);

CREATE TABLE book_category
(
    categories_id BIGINT,
    books_id     BIGINT,
    CONSTRAINT pk_bookcategory PRIMARY KEY (categories_id, books_id)
);

CREATE TABLE category
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY,
    name       VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    version    INTEGER,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE item
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY,
    description VARCHAR(255) NOT NULL,
    note        VARCHAR(1000),
    storage_id  BIGINT,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    version     INTEGER,
    CONSTRAINT pk_item PRIMARY KEY (id)
);

CREATE TABLE item_category
(
    categories_id BIGINT,
    items_id     BIGINT,
    CONSTRAINT pk_itemcategory PRIMARY KEY (categories_id, items_id)
);

CREATE TABLE publisher
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY,
    name       VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    version    INTEGER,
    CONSTRAINT pk_publisher PRIMARY KEY (id)
);

CREATE TABLE storage
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY,
    name       VARCHAR(255) NOT NULL UNIQUE,
    note       VARCHAR(500),
    parent_id  BIGINT,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    version    INTEGER,
    CONSTRAINT pk_storage PRIMARY KEY (id)
);

ALTER TABLE book
    ADD CONSTRAINT FK_BOOK_ON_PUBLISHER FOREIGN KEY (publisher_id) REFERENCES publisher (id);

ALTER TABLE book
    ADD CONSTRAINT FK_BOOK_ON_STORAGE FOREIGN KEY (storage_id) REFERENCES storage (id);

ALTER TABLE item
    ADD CONSTRAINT FK_ITEM_ON_STORAGE FOREIGN KEY (storage_id) REFERENCES storage (id);

ALTER TABLE storage
    ADD CONSTRAINT FK_STORAGE_ON_PARENT FOREIGN KEY (parent_id) REFERENCES storage (id);

ALTER TABLE book_author
    ADD CONSTRAINT fk_booaut_on_author FOREIGN KEY (authors_id) REFERENCES author (id);

ALTER TABLE book_author
    ADD CONSTRAINT fk_booaut_on_book FOREIGN KEY (books_id) REFERENCES book (id);

ALTER TABLE book_category
    ADD CONSTRAINT fk_boocat_on_book FOREIGN KEY (books_id) REFERENCES book (id);

ALTER TABLE book_category
    ADD CONSTRAINT fk_boocat_on_category FOREIGN KEY (categories_id) REFERENCES category (id);

ALTER TABLE item_category
    ADD CONSTRAINT fk_itecat_on_category FOREIGN KEY (categories_id) REFERENCES category (id);

ALTER TABLE item_category
    ADD CONSTRAINT fk_itecat_on_item FOREIGN KEY (items_id) REFERENCES item (id);
