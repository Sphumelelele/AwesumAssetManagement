<?php
require_once 'db_config.php';

$job_id = isset($_GET['job_id']) ? intval($_GET['job_id']) : 0;
if ($job_id <= 0) respond(false, null, 'job_id is required');

$stmt = $conn->prepare("SELECT photo_id, job_id, file_url, caption, uploaded_at FROM site_photo WHERE job_id = ? ORDER BY photo_id DESC");
$stmt->bind_param('i', $job_id);
$stmt->execute();
$result = $stmt->get_result();

$photos = [];
while ($row = $result->fetch_assoc()) {
    // Return an absolute URL the app can load directly
    $row['file_url'] = (isset($_SERVER['HTTPS']) ? 'https://' : 'http://') . $_SERVER['HTTP_HOST'] . dirname($_SERVER['REQUEST_URI']) . '/' . ltrim($row['file_url'], '/');
    $photos[] = $row;
}
respond(true, $photos);
