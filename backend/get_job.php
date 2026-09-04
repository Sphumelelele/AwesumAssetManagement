<?php
require_once 'db_config.php';

$job_id = isset($_GET['job_id']) ? intval($_GET['job_id']) : 0;
if ($job_id <= 0) respond(false, null, 'job_id is required');

$stmt = $conn->prepare("SELECT job_id, job_code, user_id, status, installation_address, start_time, end_time FROM job WHERE job_id = ?");
$stmt->bind_param('i', $job_id);
$stmt->execute();
$result = $stmt->get_result();

if ($row = $result->fetch_assoc()) {
    respond(true, $row);
} else {
    respond(false, null, 'Job not found');
}
