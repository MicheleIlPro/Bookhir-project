<?php
include '../connection.php';
$title = $_POST['title'];
$email = $_GET['email'];
$oggi = date('d-m-Y');
$scadenza = date('d-m-Y', strtotime('+1 month', strtotime($oggi)));
// Query the database


$sql1 = "SELECT * FROM libri WHERE titolo LIKE '{$title}' && quantita > 0";
$result1 = mysqli_query($conn, $sql1);

$sql3 = "INSERT INTO noleggi (emailUtente, titoloLibro, data_noleggio, data_scadenza) VALUES ('{$email}', '{$title}', '{$oggi}', '{$scadenza}');";
$result3 = mysqli_query($conn, $sql3);
?>

<!-- supponiamo che il nostro utente possa affittare un solo libro e non puo' ricaricare la pagina  -->

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
            <a class="nav-link" href="">Welcome </a>
          </li>
        </div>
        <div class="d-flex flex-row align-items-center">
          <li class="nav-item p-1">
            <?php echo "<a class='nav-link' href='user.php?email={$email}'>User</a>"; ?>
          </li>
          <li class="nav-item p-1">
            <a class="nav-link" href="../login.html">Logout</a>
          </li>
          <div>
          </div>
  </nav>
  <br>

  <div class="container">


    <div class="center column">
      <?php


      if (mysqli_num_rows($result1) > 0) {
        // Iterate through the results and output the data
        // $sql2 = "UPDATE libri SET quantita=quantita-1 WHERE titolo LIKE '{$title}'";
        // $result2 = mysqli_query($conn, $sql2);
        while ($row = mysqli_fetch_assoc($result1)) {
          echo "<div class='boxBook center'>";
          echo "<div class='imgBook'>";
          echo "<img src='{$row["linkImmagine"]}'>";
          echo "</div>";
          echo "<h4>", $row["titolo"], "</h4>";
          echo "<h5>", $row["descrizione"], "</h5>";
          echo "</div>";

        }
        echo "<div>";
        echo "<h4> Grazie per aver nolleggiato questo libro </h4>";
        echo "</div>";
        header("refresh: 3; url=rent.php");

      } else {
        echo "<h1>Nessun risultato trovato. <h1>";
        header("refresh: 1; url=rent.php"); // the redirect goes here
        exit;
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