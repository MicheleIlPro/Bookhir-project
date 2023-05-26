<?php
include '../connection.php';
$email = $_GET['email'];
$oggi = date('d-m-Y');
$scadenza = date('d-m-Y', strtotime('+1 month', strtotime($oggi)));

$sql4 = "INSERT INTO tessere (utenteMail, data_emissione, data_scadenza) VALUES ( '$email' ,'{$oggi}','{$scadenza}');";
$result4 = mysqli_query($conn, $sql4);
echo "ciao";
header("refresh:3; url=user.php?email={$email}");
exit;
?>