<!--changes
id img deleted Because in my code it doesn't work and  
I create class img 
add a box-shadow if you want(JB)
-->
<?php
          error_reporting(E_ALL);
          ini_set('display_errors', 1);
            
          include 'connection.php';
          $email=$_GET['email'] ?? "";
         
               
          $sql2 = "SELECT nome, cognome FROM utenti WHERE email LIKE '{$email}';";
          $result2 = mysqli_query($conn, $sql2);
       
          $sql = "SELECT * FROM libri";
          $result = mysqli_query($conn, $sql);
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/progetto-bookhir/bookhir-IT/indexPage/style.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
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
              
                  <?php if (mysqli_num_rows($result2) > 0) :?>
              <li class="nav-item p-1">
                <a class="nav-link" >
                    Welcome 
                    <?php
                      // Iterate through the results and output the data
                      $user = mysqli_fetch_assoc($result2);
                      echo  $user["nome"];
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
                <?php if (mysqli_num_rows($result2) > 0) :?>
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
        <div  class="container img">
          <h1 class="centered ">Welcome to our library</h1>
          
          <hr>
        </div>
        <br>
        <?php if (mysqli_num_rows($result2) > 0) :?>  
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
                    echo "<h4>" , $row["titolo"] , "</h5>";
                    echo "<h5>" , $row["descrizione"] , "</h5>";
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
          <!-- Footer content goes here -->
      </footer>
      
</body>
</html>

