<?php
$conectare = mysqli_connect("localhost", "root","", "destinations");
//$conectare = mysqli_connect("localhost", "contario_programare","", "contario_pw");

if(!$conectare)
{
    echo "EROARE!".'</br>';
    die(mysqli_connect_error());
}
?>