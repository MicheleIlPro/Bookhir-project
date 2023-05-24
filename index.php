<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
  
    <nav class="navbar navbar-expand-lg navbar-light bg-light container-fluid">
        <img class="logo d-inline-block align-top" src="../assets/logo_libro.png" alt="Logo">
        <div class="container">
          <a class="navbar-brand" href="#">Bookhir</a>
      </div>
          <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
      
          <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
            <ul class="navbar-nav">
              <li class="nav-item">
                <a class="nav-link">Home</a>
              </li>
              <li class="nav-item">
                <a class="nav-link">About</a>
              </li>
              <li class="nav-item">
                <a class="nav-link"  href="loading.html">Logout</a>
              </li>
            </ul>
          </div>
      </nav>
      
      <div class="container">
        <br>
        <div id="img" class="container ">
          <h1 class="centered ">Welcome to our library</h1>
          <hr>
        </div>
        <br>
        <h2>All Books</h2>
      
        <div class="row">
          <?php
            error_reporting(E_ALL);
            ini_set('display_errors', 1);
            

          $servername = "sql7.freemysqlhosting.net";
          $username = "sql7620373";
          $password = "EyJkktKdgA";
          $dbname = "sql7620373";

          // Create a connection
          $conn = mysqli_connect($servername, $username, $password, $dbname);

          // Check the connection
          if (!$conn) {
              die("Connection failed: " . mysqli_connect_error());
          }

          // Query the database
          $sql = "SELECT * FROM libri";
          $result = mysqli_query($conn, $sql);

          if (mysqli_num_rows($result) > 0) {
              // Iterate through the results and output the data
              while ($row = mysqli_fetch_assoc($result)) {
                  echo "<li>" . $row["titolo"] . "</li><br>";
              }
          } else {
              echo "No results found.";
          }

          // Close the connection
          mysqli_close($conn);
          ?>
        </div>
      </div>
      
      <footer class="footer expand-lg bg-light container-fluid">
          <!-- Footer content goes here -->
      </footer>
      
</body>
</html>
