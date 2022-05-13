<?php
$username = "s2332155";
$password = "s2332155";
$database = "d2332155";
$link  = new mysqli("127.0.0.1", $username, $password, $database);

$output =  array();

$UserId = $_REQUEST['uId'];

$sql = "SELECT Username
FROM WOOFUSER 
WHERE UserID = ?";

$stmt = $link->prepare($sql);
$stmt->bind_param("i", $UserId);
$stmt->execute();
if($result = $stmt->get_result()){
  while ($row = $result->fetch_assoc()){
    $output[]=$row;
  }
}

$stmt->close();
$link->close();
echo json_encode($output);
?>