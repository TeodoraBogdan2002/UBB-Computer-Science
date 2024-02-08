<?php
 
    $con=mysqli_connect('localhost','root','','journalsarticles');
 
    if(!$con)
    {
        die('Connection error.'.mysqli_error($con));
    }
?>