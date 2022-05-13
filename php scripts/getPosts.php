<?php
$username = "s2332155";
$password = "s2332155";
$database = "d2332155";
$link  = new mysqli("127.0.0.1", $username, $password, $database);

$output =  array();

$UserId = $_REQUEST['uId'];
$offset = $_REQUEST['offset'];

$sql = 
"SELECT
  wfu.Username, wp.PostContent, wp.DatePosted
FROM
  WOOFUSER wu
  LEFT JOIN WOOFFRIEND wf ON wu.UserID = wf.UserID
  LEFT JOIN WOOFPOST wp ON wf.FriendID = wp.UserID
  LEFT JOIN WOOFUSER wfu ON wf.FriendID = wfu.UserID
WHERE
  wu.UserID = ? AND wp.PostContent IS NOT NULL
ORDER BY wp.PostNumber DESC
LIMIT 50 OFFSET ?;";

$stmt = $link->prepare($sql);
$stmt->bind_param("ii", $UserId, $offset);
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

