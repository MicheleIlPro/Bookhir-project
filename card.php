<?php
// Database credentials
$host = 'localhost';
$username = 'your_username';
$password = 'your_password';
$database = 'your_database';

// Create a database connection
$connection = mysqli_connect($host, $username, $password, $database);
if (!$connection) {
    die("Connection failed: " . mysqli_connect_error());
}

// Retrieve book data from the database
$query = "SELECT * FROM books";
$result = mysqli_query($connection, $query);
?>

<!DOCTYPE html>
<html>
<head>
  <title>Book Cards</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>

<div class="container">
  <h2>All Books</h2>

  <div class="row">
    <?php while ($row = mysqli_fetch_assoc($result)): ?>
      <div class='boxBook center '>
                   <div class='imgBook'>
                     <img src='{$row["linkImmagine"]}'>
                 </div>
                    <h4> $row["titolo"] /h5>
                  <h5>$row["descrizione"]</h5>
                  <button type="submit" class="btn btn-primary">Rent Book</button>
                  <div>
                  <p>succssfuly rented</p>
                  <p>sorry no more book </p>
                  </div>
                  </div>
          </div>
        </div>
      </div>
    <?php endwhile; ?>
  </div>

</div>

</body>
</html>

<?php
// Close the database connection
mysqli_close($connection);
?>
