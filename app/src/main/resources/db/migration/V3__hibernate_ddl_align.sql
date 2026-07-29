ALTER TABLE book
    ALTER COLUMN year_of_publication TYPE INTEGER USING year_of_publication::INTEGER;
