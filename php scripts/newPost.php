<?php
$username = "s2332155";
$password = "s2332155";
$database = "d2332155";
$link  = new mysqli("127.0.0.1", $username, $password, $database);

$uid = $_REQUEST['uId'];
$PostContent = $_REQUEST['content'];

$sql = "INSERT INTO WOOFPOST(UserID, PostContent) VALUES(?,?);";
$stmt = $link->prepare($sql);
$stmt->bind_param("is",$uid, $PostContent);
$stmt->execute();

echo "Sucessfully Created";
$stmt->close();
$link->close();
?>
