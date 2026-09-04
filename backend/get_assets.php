<?php
require_once 'db_config.php';

$job_id = isset($_GET['job_id']) ? intval($_GET['job_id']) : 0;
if ($job_id <= 0) respond(false, null, 'job_id is required');

$stmt = $conn->prepare("SELECT asset_id, job_id, name, category, status, quantity, notes FROM asset WHERE job_id = ? ORDER BY asset_id ASC");
$stmt->bind_param('i', $job_id);
$stmt->execute();
$result = $stmt->get_result();

$assets = [];
while ($row = $result->fetch_assoc()) {
    $assets[] = $row;
}
respond(true, $assets);
