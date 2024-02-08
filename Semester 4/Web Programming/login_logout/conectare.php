<?php
$conectare = mysqli_connect("localhost", "root","", "sample_assets");

if(!$conectare)
{
    echo "EROARE!".'</br>';
    die(mysqli_connect_error());
}



?>
