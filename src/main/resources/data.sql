INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN') ON CONFLICT DO NOTHING;