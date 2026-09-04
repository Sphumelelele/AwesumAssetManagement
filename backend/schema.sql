-- =========================================================
-- AWESUM Asset Management - Database Schema
-- Import this file directly in phpMyAdmin (Import tab)
-- =========================================================

CREATE DATABASE IF NOT EXISTS awesum_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE awesum_db;

-- ---------------------------------------------------------
-- USER
-- ---------------------------------------------------------
CREATE TABLE IF NOT EXISTS user (
    user_id     INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(150) NOT NULL,
    role        VARCHAR(100) NOT NULL DEFAULT 'Installer',
    email       VARCHAR(150) NOT NULL UNIQUE,
    password    VARCHAR(255) NULL,               -- not used once Firebase manages auth
    firebase_uid VARCHAR(191) NULL UNIQUE,        -- Firebase user id (email/password or Google sign-in)
    auth_provider VARCHAR(30) NULL,               -- 'password' or 'google.com'
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ---------------------------------------------------------
-- JOB
-- ---------------------------------------------------------
CREATE TABLE IF NOT EXISTS job (
    job_id                INT AUTO_INCREMENT PRIMARY KEY,
    job_code              VARCHAR(50) NOT NULL UNIQUE,        -- e.g. JOB-2026-0083
    user_id               INT NOT NULL,                       -- created_by (FK -> user)
    status                ENUM('Active','Completed','Cancelled') DEFAULT 'Active',
    installation_address  VARCHAR(255) NOT NULL,
    start_time            DATETIME NOT NULL,
    end_time              DATETIME NULL,
    created_at            TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_job_user FOREIGN KEY (user_id) REFERENCES user(user_id)
        ON DELETE CASCADE
) ENGINE=InnoDB;

-- ---------------------------------------------------------
-- ASSET  (Tools & Assets: Dispatch / Equipment / Vehicle)
-- ---------------------------------------------------------
CREATE TABLE IF NOT EXISTS asset (
    asset_id    INT AUTO_INCREMENT PRIMARY KEY,
    job_id      INT NOT NULL,
    name        VARCHAR(150) NOT NULL,
    category    ENUM('Dispatch','Equipment','Vehicle') NOT NULL,
    status      ENUM('In Use','Out for Service') DEFAULT 'In Use',
    quantity    INT DEFAULT 1,
    notes       TEXT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_asset_job FOREIGN KEY (job_id) REFERENCES job(job_id)
        ON DELETE CASCADE
) ENGINE=InnoDB;

-- ---------------------------------------------------------
-- TEAM_MEMBER (Installation Team)
-- ---------------------------------------------------------
CREATE TABLE IF NOT EXISTS team_member (
    member_id   INT AUTO_INCREMENT PRIMARY KEY,
    job_id      INT NOT NULL,
    full_name   VARCHAR(150) NOT NULL,
    role        VARCHAR(100) NOT NULL,          -- Lead Installer / Technician / Driver-Rigger
    phone       VARCHAR(30) NULL,
    linked_user_id INT NULL,                    -- optional link back to USER ("linked to")
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_member_job FOREIGN KEY (job_id) REFERENCES job(job_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_member_user FOREIGN KEY (linked_user_id) REFERENCES user(user_id)
        ON DELETE SET NULL
) ENGINE=InnoDB;

-- ---------------------------------------------------------
-- SITE_PHOTO (Completed Work - Site Photos)
-- ---------------------------------------------------------
CREATE TABLE IF NOT EXISTS site_photo (
    photo_id    INT AUTO_INCREMENT PRIMARY KEY,
    job_id      INT NOT NULL,
    file_url    VARCHAR(500) NOT NULL,
    caption     VARCHAR(255) NULL,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_photo_job FOREIGN KEY (job_id) REFERENCES job(job_id)
        ON DELETE CASCADE
) ENGINE=InnoDB;

-- ---------------------------------------------------------
-- REFLECTION (Post-Job Reflection tab)
-- Not on the original ERD, added 1:1 with JOB to store that screen's data
-- ---------------------------------------------------------
CREATE TABLE IF NOT EXISTS reflection (
    reflection_id   INT AUTO_INCREMENT PRIMARY KEY,
    job_id          INT NOT NULL UNIQUE,
    notes           TEXT NULL,
    team_must_return TINYINT(1) DEFAULT 0,      -- 1 = Yes, 0 = No / Job Complete
    submitted       TINYINT(1) DEFAULT 0,       -- 0 = draft, 1 = submitted
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reflection_job FOREIGN KEY (job_id) REFERENCES job(job_id)
        ON DELETE CASCADE
) ENGINE=InnoDB;

-- ---------------------------------------------------------
-- Seed data (matches the screenshots supplied)
-- ---------------------------------------------------------
INSERT INTO user (user_id, name, role, email, password) VALUES
(1, 'Sipho Dlamini', 'Lead Installer', 'sipho@awesum.co.za', NULL);

INSERT INTO job (job_id, job_code, user_id, status, installation_address, start_time, end_time) VALUES
(1, 'JOB-2026-0083', 1, 'Active', '14 Rivonia Rd, Sandton, 2196', '2026-08-29 07:30:00', '2026-08-29 15:45:00');

INSERT INTO asset (job_id, name, category, status, quantity) VALUES
(1, 'Paste Brush Set (Large)', 'Dispatch', 'In Use', 1),
(1, 'Scaffold Section A', 'Equipment', 'In Use', 1),
(1, 'Van — GP 45 XY (White)', 'Vehicle', 'Out for Service', 1),
(1, 'Lift Platform #3', 'Equipment', 'In Use', 1),
(1, 'Adhesive Roller Handles x4', 'Dispatch', 'In Use', 4),
(1, 'Safety Harness Set', 'Equipment', 'In Use', 1);

INSERT INTO team_member (job_id, full_name, role, phone) VALUES
(1, 'Sipho Dlamini', 'Lead Installer', '+27 71 000 0000'),
(1, 'Thandeka Mokoena', 'Technician', NULL),
(1, 'Luca Ferreira', 'Driver / Rigger', NULL);
