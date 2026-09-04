-- =========================================================
-- Run this ONLY if you already imported the old schema.sql
-- (from before Firebase login was added) and don't want to
-- drop your database. New installs should just use schema.sql.
-- =========================================================
USE awesum_db;

ALTER TABLE user
    MODIFY password VARCHAR(255) NULL,
    ADD COLUMN firebase_uid VARCHAR(191) NULL UNIQUE AFTER password,
    ADD COLUMN auth_provider VARCHAR(30) NULL AFTER firebase_uid;
