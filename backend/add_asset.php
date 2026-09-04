<?php
require_once 'db_config.php';

$job_id    = isset($_POST['job_id']) ? intval($_POST['job_id']) : 0;
$name      = trim($_POST['name'] ?? '');
$category  = trim($_POST['category'] ?? 'Dispatch');
$status    = trim($_POST['status'] ?? 'In Use');
$quantity  = isset($_POST['quantity']) ? intval($_POST['quantity']) : 1;
$notes     = trim($_POST['notes'] ?? '');

if ($job_id <= 0 || $name === '') {
    respond(false, null, 'job_id and name are required');
}

$stmt = $conn->prepare("INSERT INTO asset (job_id, name, category, status, quantity, notes) VALUES (?, ?, ?, ?, ?, ?)");
$stmt->bind_param('isssis', $job_id, $name, $category, $status, $quantity, $notes);

if ($stmt->execute()) {
    respond(true, ['asset_id' => $stmt->insert_id], 'Asset saved');
} else {
    respond(false, null, 'Failed to save asset: ' . $stmt->error);
}
