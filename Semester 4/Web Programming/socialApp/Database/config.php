<?php
/**
 * Created by PhpStorm.
 * User: potra
 * Date: 26.06.2018
 * Time: 21:32
 */

define('DB_SERVER', 'localhost');
define('DB_USERNAME', 'root');
define('DB_PASSWORD', '');
define('DB_DATABASE', 'exam_friends');
$GLOBALS['DB_CONNECTION'] = mysqli_connect(DB_SERVER,DB_USERNAME,DB_PASSWORD,DB_DATABASE);



