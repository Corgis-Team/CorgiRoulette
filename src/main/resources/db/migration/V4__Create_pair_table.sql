CREATE TABLE IF NOT EXISTS users_opponents
(
    chosen_user_id     BIGINT NOT NULL,
    opponent_user_id BIGINT NOT NULL,
    CONSTRAINT fk_user_competition FOREIGN KEY (opponent_user_id) REFERENCES users (id),
    CONSTRAINT fk_competitor_competition FOREIGN KEY (chosen_user_id) REFERENCES users (id)
    );