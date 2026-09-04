<?php
require_once 'db_config.php';

$job_id           = isset($_POST['job_id']) ? intval($_POST['job_id']) : 0;
$notes            = trim($_POST['notes'] ?? '');
$team_must_return = isset($_POST['team_must_return']) ? intval($_POST['team_must_return']) : 0;
$submitted        = isset($_POST['submitted']) ? intval($_POST['submitted']) : 0;

if ($job_id <= 0) respond(false, null, 'job_id is required');

$stmt = $conn->prepare(
    "INSERT INTO reflection (job_id, notes, team_must_return, submitted) VALUES (?, ?, ?, ?)
     ON DUPLICATE KEY UPDATE notes = VALUES(notes), team_must_return = VALUES(team_must_return), submitted = VALUES(submitted)"
);
$stmt->bind_param('isii', $job_id, $notes, $team_must_return, $submitted);

if ($stmt->execute()) {
    respond(true, null, $submitted ? 'Reflection submitted' : 'Draft saved');
} else {
    respond(false, null, 'Failed to save reflection: ' . $stmt->error);
}
