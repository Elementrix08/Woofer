<?php
$username = "s2332155";
$password = "s2332155";
$database = "d2332155";
$link  = new mysqli("127.0.0.1", $username, $password, $database);

$userId = $_REQUEST['uId'];
$friendId = $_REQUEST['fId'];

$sql = "DELETE FROM WOOFPENDINGREQUESTS WHERE UserID = ? AND FriendID = ?;";
$stmt = $link->prepare($sql);
$stmt->bind_param("ii",$userId,$friendId);
$stmt->execute();

echo "Sucessfully deleted";
$stmt->close();
$link->close();
?>
