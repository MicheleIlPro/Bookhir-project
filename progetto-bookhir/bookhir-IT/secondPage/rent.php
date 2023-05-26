<?php
include '../connection.php';
$email = $_GET['email'] ?? "";
$sql2 = "SELECT nome, cognome FROM utenti WHERE email LIKE '{$email}';";
$result2 = mysqli_query($conn, $sql2);
?>

<!-- supponiamo che il nostro utente possa affittare un solo libro  ricorda decommentare la query per il rent -->

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="../indexPage/style.css">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
    integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>

<body>

  <nav class="navbar navbar-expand-lg navbar-light bg-light container-fluid">
    <img class="logo d-inline-block align-top" src="../assets/logo_libro.png" alt="Logo">
    <div class="container">
      <a class="navbar-brand" href="index.php">Bookhir</a>
    </div>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
      aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
      <ul class="navbar-nav">
        <div class="centered d-flex flex-row align-items-center">
          <li class="nav-item p-1">
            <a class="nav-link">
              Welcome
              <?php
              $user = mysqli_fetch_assoc($result2);
              echo $user["nome"], "   ", $user["cognome"];
              ?>
            </a>
          </li>
        </div>
        <div class="d-flex flex-row align-items-center">
          <li class="nav-item p-1">
            <?php echo "<a class='nav-link' href='user.php?email={$email}'>User</a>"; ?>
          </li>
          <li class="nav-item p-1">
            <a class="nav-link" href="logout.html">Logout</a>
          </li>
          <div>
          </div>
  </nav>
  <br>

  <div class="container">
    <h2>All Books</h2>
    <div class="container">
      <?php echo "<form action='search.php?email={$email}' method='post' class='mb-4'>"; ?>
      <div class="form-row align-items-center">
        <div class="col-md-8">
          <input type="text" name="title" class="form-control" placeholder="Cerca il titolo" required>
        </div>
        <div class="col-md-4">
          <button type="submit" class="btn btn-primary">Nolleggia</button>
        </div>
      </div>
      </form>
    </div>
    <div class="row">
      <?php // Query the database
      $sql = "SELECT * FROM libri WHERE QUANTITA > 0";
      $result = mysqli_query($conn, $sql);

      if (mysqli_num_rows($result) > 0) {
        // Iterate through the results and output the data
        $count = 0;
        while ($row = mysqli_fetch_assoc($result)) {
          echo "<div class='boxBook center'>";
          echo "<div class='imgBook'>";
          echo "<img src='{$row["linkImmagine"]}'>";
          echo "</div>";
          echo "<h4>", $row["titolo"], "</h4>";
          echo "<h5>", $row["descrizione"], "</h5>";
          echo "<h5>", $row["autore"], "</h5>";
          echo "<h5>Quantità:  ", $row["quantita"], "</h5>";
          echo "</div>";

        }
      } else {
        echo "Nessun risultato trovato.";
      }

      // Close the connection
      mysqli_close($conn);

      ?>
    </div>
  </div>
  <footer class="footer">
    <div class="container">
      <span class="text-muted"> © 2021 Copyright Bookhir</span>
    </div>
  </footer>
</body>

</html>