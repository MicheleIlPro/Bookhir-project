<!--changes
id img deleted Because in my code it doesn't work and  
I create class img 
add a box-shadow if you want(JB)
-->
<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);

include 'connection.php';
$email = $_GET['email'] ?? "";


$sql2 = "SELECT nome, cognome FROM utenti WHERE email LIKE '{$email}';";
$result2 = mysqli_query($conn, $sql2);

$sql = "SELECT * FROM libri";
$result = mysqli_query($conn, $sql);

$sql1 ="SELECT * FROM libri LIMIT 4";
$result1 = mysqli_query($conn, $sql1);
?>

<!-- Inizio HTML -->

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="/progetto-bookhir/bookhir-IT/indexPage/style.css">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
    integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>

<body>


  <nav class="navbar navbar-expand-lg navbar-light bg-light container-fluid">
    <img class="logo d-inline-block align-top" src="/progetto-bookhir/bookhir-IT/assets/logo_libro.png" alt="Logo">
    <div class="container">
      <a class="navbar-brand" href="index.php">Bookhir</a>
    </div><!--div-->
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
      aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button><!--button-->

    <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
      <ul class="navbar-nav">
        <div class="centered d-flex flex-row align-items-center">

          <?php if (mysqli_num_rows($result2) > 0): ?>
            <li class="nav-item p-1">
              <a class="nav-link">
              
                <?php
                // Iterate through the results and output the data
                $user = mysqli_fetch_assoc($result2);
                echo "{$user["nome"]} {$user["cognome"]}";
                ?>
              </a>
            </li>
          <?php else: ?>
            <li class="nav-item p-1">
              <a class="nav-link">Not Logged</a>
            </li>
          <?php endif ?>
        </div>
        <div class="d-flex flex-row align-items-center">
          <?php if (mysqli_num_rows($result2) > 0): ?>
            <li class="nav-item p-1">
              <?php
              echo "<a class='nav-link' href='/progetto-bookhir/bookhir-IT/secondPage/rent.php?email={$email}'>Rent</a>";
              ?>
            </li>
            <li class="nav-item p-1">
              <?php
              echo "<a class='nav-link' href='/progetto-bookhir/bookhir-IT/secondPage/user.php?email={$email}'>User</a>"
                ?>
            </li>
            <li class="nav-item ">
              <a class="nav-link" href="/progetto-bookhir/bookhir-IT/secondPage/about.html">About</a>
            </li>
            <li class="nav-item p-1">
              <a class="nav-link" href="/progetto-bookhir/bookhir-IT/secondPage/logout.html">Logout</a>
            </li>
          
            <li class="nav-item p-1">
            <button class="flagIT hide" id="ita" onclick="translateToItalian()"></button>
              <button class="flagEN show" id="english" onclick="translateToEnglish()"></button>
            </li>

          <?php else: ?>
            <li class="nav-item ">
              <a class="nav-link" href="/progetto-bookhir/bookhir-IT/secondPage/about.html">About</a>
            </li>
            <li class="nav-item p-1">
              <a class="nav-link" href="login.html">Login</a>
            </li>
          <?php endif ?>
      </ul>
    </div>
    </div>
  </nav>

  <div class="container">
    <br>
    <div class="container img">
      <h1 class="centered ">Welcome to our library</h1>

      <hr>
    </div>
    <br>
    <?php if (mysqli_num_rows($result2) > 0): ?>
      <div>

        <h2>All Books</h2>
      </div>

      <div class="row center">
        <?php

        // Query the database
      


        // Iterate through the results and output the data
      
        while ($row = mysqli_fetch_assoc($result)) {
          echo "<div class='boxBook center '>";
          echo "<div class='imgBook'>";
          echo "<img src='{$row["linkImmagine"]}'>";
          echo "</div>";
          echo "<h4>", $row["titolo"], "</h5>";
          echo "<h5>", $row["descrizione"], "</h5>";
          echo "</div>";
        }
        ?>
      <?php else: ?>

      </div>
    </div>
  <?php endif ?>
  <?php
  // Close the connection
  mysqli_close($conn);
  ?>
 <footer class="footer expand-lg bg-light container-fluid">
    <section class="d-flex justify-content-center justify-content-lg-between p-4 border-bottom">
    </section>
    <section class="">
      <div class="container text-center text-md-start mt-5">
        <div class="row mt-3">
          <div class="col-md-3 col-lg-4 col-xl-3 mx-auto mb-4">
            <h6 class="text-uppercase fw-bold mb-4">
              <i class="fas fa-gem me-3 text-secondary"></i>Company name
            </h6>
            <p>
              Here you can use rows and columns to organize your footer content. Lorem ipsum
              dolor sit amet, consectetur adipisicing elit.
            </p>
          </div>
          <div class="col-md-2 col-lg-2 col-xl-2 mx-auto mb-4">
            <!-- Links -->
            <h6 class="text-uppercase fw-bold mb-4">
              Latest Books
            </h6>
            <?php 
           
            
            while ($row1=mysqli_fetch_assoc($result1)) {
             
              echo "<p>", $row1["titolo"], "</p>";
              
            }
            ?>
            
          </div>
          <!-- Grid column -->
<!-- Grid column -->
          <div class="col-md-3 col-lg-2 col-xl-2 mx-auto mb-4">
            <!-- Links -->
            <h6 class="text-uppercase fw-bold mb-4">
              Social media links
            </h6>
            <p>
              <a href="#!" class="text-reset">Linkedln</a>
            </p>
            <p>
              <a href="#!" class="text-reset">Instgram</a>
            </p>
            <p>
              <a href="#!" class="text-reset">Facebook</a>
            </p>
            <p>
              <a href="#!" class="text-reset">Github</a>
            </p>
          </div>
          <!-- Grid column -->

          <!-- Grid column -->
          <div class="col-md-4 col-lg-3 col-xl-3 mx-auto mb-md-0 mb-4">
            <!-- Links -->
            <h6 class="text-uppercase fw-bold mb-4">Contact</h6>
            <p><i class="fas fa-home me-3 text-secondary"></i> Rome, Rm 12345, IT
          </p>
            <p>
              <i class="fas fa-envelope me-3 text-secondary"></i>
              bookhim@example.com
            </p>
            <p><i class="fas fa-phone me-3 text-secondary"></i> + 01 234 567 88</p>
            <p><i class="fas fa-print me-3 text-secondary"></i> + 01 234 567 89</p>
          </div>
          <!-- Grid column -->
        </div>
        <!-- Grid row -->
      </div>
    </section>
    <!-- Section: Links  -->

    <!-- Copyright -->
    <div class="text-center p-4" style="background-color: rgba(0, 0, 0, 0.025);">
      © 2021 Copyright  Bookhir
    </div>
    <!-- Copyright -->

  </footer>
  <script type="text/javascript" src="/progetto-bookhir/bookhir-IT/secondPage/scriptsTranslation.js"></script>
</body>

</html>