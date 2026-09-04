<?php
require_once 'db_config.php';

// Called from the app immediately after a successful Firebase sign-in
// (email/password OR Google). Keeps our MySQL `user` table (phpMyAdmin)
// as the source of truth for job/asset/team ownership.

$firebase_uid  = trim($_POST['firebase_uid'] ?? '');
$name          = trim($_POST['name'] ?? '');
$email         = trim($_POST['email'] ?? '');
$auth_provider = trim($_POST['auth_provider'] ?? 'password');
$role          = trim($_POST['role'] ?? 'Installer');

if ($firebase_uid === '' || $email === '') {
    respond(false, null, 'firebase_uid and email are required');
}
if ($name === '') $name = explode('@', $email)[0];

// Look up by firebase_uid first, then fall back to email (covers a user who
// registered with email/password and later signs in with Google using the
// same email address).
$stmt = $conn->prepare("SELECT user_id FROM user WHERE firebase_uid = ? OR email = ? LIMIT 1");
$stmt->bind_param('ss', $firebase_uid, $email);
$stmt->execute();
$existing = $stmt->get_result()->fetch_assoc();

if ($existing) {
    $user_id = $existing['user_id'];
    $update = $conn->prepare("UPDATE user SET name = ?, firebase_uid = ?, auth_provider = ? WHERE user_id = ?");
    $update->bind_param('sssi', $name, $firebase_uid, $auth_provider, $user_id);
    $update->execute();
} else {
    $insert = $conn->prepare("INSERT INTO user (name, role, email, firebase_uid, auth_provider) VALUES (?, ?, ?, ?, ?)");
    $insert->bind_param('sssss', $name, $role, $email, $firebase_uid, $auth_provider);
    $insert->execute();
    $user_id = $insert->insert_id;
}

respond(true, ['user_id' => $user_id, 'name' => $name, 'email' => $email], 'User synced');
