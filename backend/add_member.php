<?php
require_once 'db_config.php';

$job_id    = isset($_POST['job_id']) ? intval($_POST['job_id']) : 0;
$full_name = trim($_POST['full_name'] ?? '');
$role      = trim($_POST['role'] ?? '');
$phone     = trim($_POST['phone'] ?? '');

if ($job_id <= 0 || $full_name === '' || $role === '') {
    respond(false, null, 'job_id, full_name and role are required');
}

$stmt = $conn->prepare("INSERT INTO team_member (job_id, full_name, role, phone) VALUES (?, ?, ?, ?)");
$stmt->bind_param('isss', $job_id, $full_name, $role, $phone);

if ($stmt->execute()) {
    respond(true, ['member_id' => $stmt->insert_id], 'Team member added');
} else {
    respond(false, null, 'Failed to add member: ' . $stmt->error);
}
