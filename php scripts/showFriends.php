<?php
$username = "s2332155";
$password = "s2332155";
$database = "d2332155";
$link  = new mysqli("127.0.0.1", $username, $password, $database);
$output = array();

$param = $_REQUEST['parameter'];
$un = $param === 'search' ? $_REQUEST['uName'] . "%" : $_REQUEST['uName'];

$sql = "SELECT * FROM WOOFUSER WHERE Username ";
$sql .=  $param === "search" ? "LIKE ?" : "= ?";

$stmt = $link->prepare($sql);
$stmt->bind_param("s", $un);
$stmt->execute();
if($result = $stmt->get_result()){
  while ($row = $result->fetch_assoc()){
    $output[]=$row;
  }
}
$stmt ->close();
$link->close();
echo json_encode($output);
?>
