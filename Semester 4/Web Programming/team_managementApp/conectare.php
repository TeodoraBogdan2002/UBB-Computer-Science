<?php
$conectare = mysqli_connect("localhost", "root","", "exam_players");

if(!$conectare)
{
    echo "EROARE!".'</br>';
    die(mysqli_connect_error());
}



?>
