CREATE TABLE IF NOT EXISTS client (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    age INTEGER NOT NULL,
    subscription_end DATE NOT NULL
);

-- Создание таблицы тренировок
CREATE TABLE IF NOT EXISTS training (
    id SERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    duration INTEGER NOT NULL,
    difficulty VARCHAR(20) NOT NULL
);

-- Создание таблицы посещений
CREATE TABLE IF NOT EXISTS visit (
    id SERIAL PRIMARY KEY,
    client_id INTEGER REFERENCES client(id),
    training_id INTEGER REFERENCES training(id),
    visit_date DATE NOT NULL,
    calories_burned INTEGER NOT NULL
);

-- Вставка тестовых данных для клиентов
INSERT INTO client (full_name, age, subscription_end)
VALUES
    ('Иванов Иван Иванович', 30, '2025-12-31'),
    ('Петров Петр Петрович', 25, '2025-10-15');

-- Вставка тестовых данных для тренировок
INSERT INTO training (type, duration, difficulty)
VALUES
    ('бег', 30, 'средняя'),
    ('йога', 45, 'легкая');

-- Вставка тестовых данных для посещений
INSERT INTO visit (client_id, training_id, visit_date, calories_burned)
VALUES
    (1, 1, CURRENT_DATE, 300),
    (2, 2, CURRENT_DATE, 200),
    (1, 2, CURRENT_DATE, 200);
