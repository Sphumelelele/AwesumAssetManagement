<?php
require_once 'db_config.php';

$job_id  = isset($_POST['job_id']) ? intval($_POST['job_id']) : 0;
$caption = trim($_POST['caption'] ?? '');

if ($job_id <= 0) respond(false, null, 'job_id is required');
if (!isset($_FILES['photo']) || $_FILES['photo']['error'] !== UPLOAD_ERR_OK) {
    respond(false, null, 'No photo uploaded');
}

$allowed = ['jpg', 'jpeg', 'png', 'webp'];
$ext = strtolower(pathinfo($_FILES['photo']['name'], PATHINFO_EXTENSION));
if (!in_array($ext, $allowed)) {
    respond(false, null, 'Invalid file type. Allowed: PNG, JPG, WEBP');
}

if ($_FILES['photo']['size'] > 20 * 1024 * 1024) {
    respond(false, null, 'File too large. Max 20MB');
}

$uploadDir = __DIR__ . '/uploads/';
if (!is_dir($uploadDir)) mkdir($uploadDir, 0755, true);

$fileName = 'job' . $job_id . '_' . time() . '_' . uniqid() . '.' . $ext;
$destPath = $uploadDir . $fileName;

if (!move_uploaded_file($_FILES['photo']['tmp_name'], $destPath)) {
    respond(false, null, 'Failed to save uploaded file');
}

$relativeUrl = 'uploads/' . $fileName;

$stmt = $conn->prepare("INSERT INTO site_photo (job_id, file_url, caption) VALUES (?, ?, ?)");
$stmt->bind_param('iss', $job_id, $relativeUrl, $caption);

if ($stmt->execute()) {
    $fullUrl = (isset($_SERVER['HTTPS']) ? 'https://' : 'http://') . $_SERVER['HTTP_HOST'] . dirname($_SERVER['REQUEST_URI']) . '/' . $relativeUrl;
    respond(true, ['photo_id' => $stmt->insert_id, 'file_url' => $fullUrl], 'Photo uploaded');
} else {
    respond(false, null, 'DB insert failed: ' . $stmt->error);
}
