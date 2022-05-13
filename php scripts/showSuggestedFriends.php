<?php
$username = "s2332155";
$password = "s2332155";
$database = "d2332155";
$link  = new mysqli("127.0.0.1", $username, $password, $database);

$output =  array();

$userID = $_REQUEST['uId'];

$sql = "SELECT deriv.UserID, deriv.FriendID, wu2.Username, `Number of mutual friends`
FROM
(SELECT
  wu.UserID, wf2.FriendID, COUNT(wf2.FriendID) AS `Number of mutual friends`
FROM
  WOOFUSER wu
  LEFT JOIN WOOFFRIEND wf ON wu.UserID = wf.UserID
  LEFT JOIN WOOFFRIEND wf2 ON wf.FriendID = wf2.UserID
    Group By wu.UserID, wf2.FriendID
    HAVING wu.UserID != wf2.FriendID
    ORDER BY `Number of mutual friends` DESC) deriv
LEFT JOIN WOOFFRIEND wf3 ON wf3.FriendID = deriv.FriendID AND wf3.UserID = deriv.UserID
INNER JOIN WOOFUSER wu2 ON deriv.FriendID = wu2.UserID
WHERE wf3.UserID IS NULL AND deriv.UserID = ?;";

$stmt = $link->prepare($sql);
$stmt->bind_param("i",$userID);
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
