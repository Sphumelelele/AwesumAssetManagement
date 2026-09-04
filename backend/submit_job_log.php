<?php
require_once 'db_config.php';

$job_id = isset($_POST['job_id']) ? intval($_POST['job_id']) : 0;
$draft  = isset($_POST['draft']) ? intval($_POST['draft']) : 0; // 1 = Save Draft, 0 = Submit

if ($job_id <= 0) respond(false, null, 'job_id is required');

if ($draft) {
    // Save Draft: no status change, just acknowledge
    respond(true, null, 'Draft saved');
}

$stmt = $conn->prepare("UPDATE job SET status = 'Completed' WHERE job_id = ?");
$stmt->bind_param('i', $job_id);

if ($stmt->execute()) {
    respond(true, null, 'Job log submitted');
} else {
    respond(false, null, 'Failed to submit job log: ' . $stmt->error);
}
