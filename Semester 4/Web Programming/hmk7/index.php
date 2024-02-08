<?php

require 'conectare.php';
session_start();
?>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="addStyles.css">
</head>
<body>  
      <h1>Vacation Manager</h1>
    <?php
    // if( $_SESSION['message'] != '')
    //     echo '<div class="alert alert-warning" role="alert">'.$_SESSION['message'].'
    // </div>';
    session_destroy();
    
    ?>
    
    <div class="row">   
        <a href="add-destination.php" type="button"  class="index-button button-red">Add Destination</a>
        <a href="delete-destinations.php" type="button"  class="index-button button-mov">Delete Destinations</a>
        <a href="modify-destinations.php" type="button"  class="index-button button-green">Modify Destinations</a>
        <a href="browse-destinations.php" type="button"  class="index-button button-orange">Browse Destinations</a>
    </div>
</body>
</html>