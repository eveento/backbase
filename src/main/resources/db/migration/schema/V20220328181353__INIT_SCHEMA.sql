CREATE SEQUENCE IF NOT EXISTS my_seq INCREMENT 1 START 1 CACHE 100;

CREATE TABLE IF NOT EXISTS academy_award (
    id INT PRIMARY KEY DEFAULT NEXTVAL('my_seq'),
    year VARCHAR(25) NOT NULL,
    category VARCHAR(255) NOT NULL,
    nominee TEXT NOT NULL,
    description VARCHAR(255),
    is_win BOOLEAN DEFAULT FALSE
    );

create index category__academy_award_idx on academy_award(category);