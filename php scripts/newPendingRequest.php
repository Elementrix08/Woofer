<?php
$username = "s2332155";
$password = "s2332155";
$database = "d2332155";
$link  = new mysqli("127.0.0.1", $username, $password, $database);

$userId = $_REQUEST['uId'];
$friendId = $_REQUEST['fId'];

$sql = "INSERT INTO WOOFPENDINGREQUESTS(UserID, FriendID) VALUES(?,?);";
$stmt = $link->prepare($sql);
$stmt->bind_param("ii",$userId,$friendId);
$stmt->execute();

echo "Sucessfully created";
$stmt->close();
$link->close();
?>
