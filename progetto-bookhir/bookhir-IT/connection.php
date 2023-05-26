<?php
$servername = "sql7.freemysqlhosting.net";
$username = "sql7620373";
$password = "EyJkktKdgA";
$dbname = "sql7620373";

$conn = mysqli_connect($servername, $username, $password, $dbname);

// Verifica la connessione
if (!$conn) {
    die("Connessione al database fallita: " . mysqli_connect_error());
}
?>