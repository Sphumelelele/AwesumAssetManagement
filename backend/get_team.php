<?php
require_once 'db_config.php';

$job_id = isset($_GET['job_id']) ? intval($_GET['job_id']) : 0;
if ($job_id <= 0) respond(false, null, 'job_id is required');

$stmt = $conn->prepare("SELECT member_id, job_id, full_name, role, phone FROM team_member WHERE job_id = ? ORDER BY member_id ASC");
$stmt->bind_param('i', $job_id);
$stmt->execute();
$result = $stmt->get_result();

$members = [];
while ($row = $result->fetch_assoc()) {
    $members[] = $row;
}
respond(true, $members);
