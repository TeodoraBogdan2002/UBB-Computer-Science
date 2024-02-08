<?php
    
    require 'conectare.php';

    $sql ="SELECT * FROM destinations";
    $result = mysqli_query($conectare, $sql);
    $page_selected = 1;  
    if (isset ($_GET['page'])) {  
        $page_selected = $_GET['page'];  
    }  
    //var_dump($result);
    $results_per_page = 4;  
    $page_first_result = ($page_selected - 1) * $results_per_page;

    $number_of_result = mysqli_num_rows($result);  

    $number_of_page = ceil ($number_of_result / $results_per_page);  
    
    $query = "SELECT * FROM destinations LIMIT " . $page_first_result . ',' . $results_per_page;  
    $new_result = mysqli_query($conectare, $query);  
    //var_dump($new_result);
?>


<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="addStyles.css">

</head>
<body>
    <div class="container mt-10">
    <div class="row">
      <ul class="list-group" style="margin-top:100px;">
      <?php
while ($row = mysqli_fetch_object($new_result)) {
  echo '<li class="list-group-item custom-li">';
  echo '<div class="name-box">';
  echo '<span class="name-label">' . $row->name . '</span>';
  echo '<a href="delete-destination-backend.php?id=' . $row->id . '" class="btn delete-button">X</a>';
  echo '</div>';
  echo '</li>';
}
?>
      </ul>
      <ul class="pagination mt-10 custom-pagination">
        <?php
        for($page = 1; $page <= $number_of_page; $page++) {  
            if($page == $page_selected)
            echo '<li class="page-item active"><a class="page-link" href="delete-destinations.php?page='.$page.'">'.$page.'</a></li>';
            else
            echo '<li class="page-item"><a class="page-link" href="delete-destinations.php?page='.$page.'">'.$page.'</a></li>';
        }
        ?>
        </ul>
      <a href="index.php" type="button" class="btn btn-danger">Back</a>
    </div>
    </div>
</body>
</html>