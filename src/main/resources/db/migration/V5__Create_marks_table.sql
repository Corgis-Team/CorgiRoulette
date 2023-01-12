CREATE TABLE IF NOT EXISTS marks
(
    id          BIGSERIAL NOT NULL,
    user_id     BIGSERIAL NOT NULL,
    mark        DECIMAL(4,1) NOT NULL,
    date_time   TIMESTAMP NOT NULL,
    constraint pk_mark primary key (id),
    CONSTRAINT fk_user_mark FOREIGN KEY (user_id) REFERENCES users (id)
    );