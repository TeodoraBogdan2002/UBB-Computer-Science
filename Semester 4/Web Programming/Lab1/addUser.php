<html>
<body>

<!-- Welcome <?php echo $_POST["username"]; ?><br>
Your email address is: <?php echo $_POST["email"]; ?>
  Welcomexx <?php echo $_POST["password"]; ?><br>
Your email address isxx: <?php echo $_POST["fullname"]; ?> -->
  
  
  <?php

            $con = new mysqli("localhost", "root", "", "employee");

            if (!$con){
                die('Could not connect: ' . mysqli_error());
            }
            
            if ($_SERVER["REQUEST_METHOD"] == "POST"){
                $fname = mysqli_real_escape_string($con,$_POST["first_name"]);
                $lname = mysqli_real_escape_string($con,$_POST["last_name"]);
                $id_employee = mysqli_real_escape_string($con,$_POST["id_employee"]);
                $phonenr = mysqli_real_escape_string($con,$_POST["phone_number"]);
                $dateOfBirth = mysqli_real_escape_string($con,$_POST["date_of_birth"]);

                // echo $_POST["username"]; 

                $sql = "INSERT INTO employees (first_name, last_name, id_employee, phone_number, date_of_birth) VALUES ( '$fname', '$lname', '$id_employee','$phonenr', '$dateOfBirth')";
                
               if (!mysqli_query($con, $sql)) {
					printf("%d Row inserted.\n", mysqli_affected_rows($con));
				}
                mysqli_close($con);
            }
        ?>
  </body>
</html>