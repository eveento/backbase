CREATE SEQUENCE IF NOT EXISTS my_seq INCREMENT 1 START 1 CACHE 100;

CREATE TABLE IF NOT EXISTS academy_award (
    id INT PRIMARY KEY DEFAULT NEXTVAL('my_seq'),
    year VARCHAR(25) NOT NULL,
    category VARCHAR(255) NOT NULL,
    nominee TEXT NOT NULL,
    description VARCHAR(255),
    win BOOLEAN DEFAULT FALSE
    );

create index academy_award_nominee_category_is_win_idx on academy_award(nominee,category,win);