<?php
$username = "s2332155";
$password = "s2332155";
$database = "d2332155";
$link  = new mysqli("127.0.0.1", $username, $password, $database);

$UserEmail = $_REQUEST['email'];
$un = $_REQUEST['uName'];
$pw = $_REQUEST['pwd'];

$sql = "INSERT INTO WOOFUSER(UserEmail, Username, HashedPassword) VALUES(?,?,?);";
$stmt = $link->prepare($sql);
$stmt->bind_param("sss",$UserEmail,$un,$pw);
$stmt->execute();

echo "sucessfully created";

$stmt->close();
$link->close();
?>

