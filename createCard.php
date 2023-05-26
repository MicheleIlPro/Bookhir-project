<?php
// Retrieve form data
$name = $_POST['name'];
$surname = $_POST['surname'];
$password = $_POST['password'];
$dob = $_POST['dob'];

// Connect to the MySQL database
$servername = "localhost";
$username = "your_username";
$password = "your_password";
$dbname = "your_database_name";

$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Insert data into the database
$sql = "INSERT INTO library_cards (name, surname, password, dob) VALUES ('$name', '$surname', '$password', '$dob')";

if ($conn->query($sql) === TRUE) {
    echo "Registration successful!";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

// Close the database connection
$conn->close();
?>
