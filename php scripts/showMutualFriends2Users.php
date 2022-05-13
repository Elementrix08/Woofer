<?php
$username = "s2332155";
$password = "s2332155";
$database = "d2332155";
$link  = new mysqli("127.0.0.1", $username, $password, $database);

$output =  array();

$user1 = $_REQUEST['uId'];
$user2 = $_REQUEST['fId'];

$sql = "SELECT FriendID, UserID, UserEmail, Username
FROM
    (SELECT wf.FriendID
    FROM WOOFFRIEND wf
    LEFT JOIN WOOFFRIEND wf2
    ON wf.FriendID = wf2.UserID
    WHERE wf.UserID = ? AND wf2.FriendID = ?) deriv
INNER JOIN WOOFUSER wu ON deriv.FriendID = wu.UserID;";

$stmt = $link->prepare($sql);
$stmt->bind_param("ii",$user1,$user2);
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
