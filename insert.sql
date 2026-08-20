-- 1. Insertion des utilisateurs
INSERT INTO "user" (id, ref, first_name, last_name, email, phone) VALUES
                                                                      ('USR01', 'REF-000001', 'John', 'Doe', 'john.doe@example.com', '+33612345678'),
                                                                      ('USR02', 'REF-000002', 'Jane', 'Smith', 'jane.smith@example.com', '+33687654321');

-- 2. Insertion de CashFlow basiques (optionnel)
INSERT INTO cashflow (id, created_at, amount, id_user) VALUES
    ('CF001', NOW(), 100.00, 'USR01');

-- 3. Insertion de Donations (héritent des champs de cashflow)
INSERT INTO donation (id, created_at, amount, id_user, comment) VALUES
                                                                    ('DON01', '2026-01-15 10:00:00', 50.00, 'USR01', 'Soutien au projet Humanitaire'),
                                                                    ('DON02', '2026-02-01 14:30:00', 200.00, 'USR02', 'Don annuel membres');

-- 4. Insertion d'Expenses (héritent des champs de cashflow)
INSERT INTO expense (id, created_at, amount, id_user, reason, frequency) VALUES
                                                                             ('EXP01', '2026-01-01 08:00:00', 15.99, 'USR01', 'Abonnement Serveur Cloud', 'MONTHLY'),
                                                                             ('EXP02', '2026-01-10 12:00:00', 45.00, 'USR01', 'Fournitures de bureau', 'NONE'),
                                                                             ('EXP03', '2026-02-05 09:15:00', 120.00, 'USR02', 'Licence logicielle annuelle', 'YEARLY');