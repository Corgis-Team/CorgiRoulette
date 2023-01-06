CREATE TABLE IF NOT EXISTS pairs
(
    user_id     BIGINT NOT NULL,
    opponent_id BIGINT NOT NULL,
    CONSTRAINT fk_user_competition FOREIGN KEY (opponent_id) REFERENCES users (id),
    CONSTRAINT fk_competitor_competition FOREIGN KEY (user_id) REFERENCES users (id)
);