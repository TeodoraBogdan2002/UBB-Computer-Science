<?php

class DatabaseUtils {
    private string $host = 'localhost';
    private string $database = 'exam6';
    private string $user = 'root';
    private string $password = '';
    private string $charset = 'utf8';

    private PDO $pdo;
    private string $error;

    public function __construct() {
        $dsn = "mysql:host=$this->host;dbname=$this->database;charset=$this->charset";
        $opt = array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
            PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
            PDO::ATTR_EMULATE_PREPARES => false);
        try {
            $this->pdo = new PDO($dsn, $this->user, $this->password, $opt);
        } catch (PDOException $e) {
            $this->error = $e->getMessage();
            echo "Error connecting to DB: " . $this->error;
        }
    }


    public function selectChannelsByPerson($person): array {
        $statement = $this->pdo->query("SELECT * FROM Channels C WHERE C.ownerid = (SELECT id FROM Persons P WHERE P.name = '$person')");
        return $statement->fetchAll(PDO::FETCH_ASSOC);
    }

    public function subscribeToChannel($channelId, $user): void {
        $statement = $this->pdo->query("SELECT * FROM Channels C WHERE C.id =" . "$channelId");
        $array = $statement->fetchAll(PDO::FETCH_ASSOC);
        $subscribers = $array[0]["subscribers"];
        $subscribers_array = explode(",", $subscribers);
        $new_string = "";
        $ok = 0;
        foreach ($subscribers_array as $sub_date) {
            $sub_date_array = explode('-', $sub_date);
            if ($sub_date_array[0] == $user) {
                if ($new_string != "") {
                    $new_string = $new_string . ',' . $sub_date_array[0] . '-' . date('d/m/Y');
                } else {
                    $new_string = $sub_date_array[0] . '-' . date('d/m/Y');
                }
                $ok = 1;
            } else {
                if ($new_string != "") {
                    $new_string = $new_string . ',' . $sub_date;
                } else {
                    $new_string = $sub_date;
                }
            }
        }
        if ($ok === 0) {
            if ($new_string != "") {
                $new_string = $new_string . "," . $user . '-' . date('d/m/Y');
            } else {
                $new_string = $user . '-' . date('d/m/Y');
            }
        }
        $this->pdo->exec("UPDATE Channels SET subscribers = '$new_string' WHERE id =" . "$channelId");
    }

    public function getSubscribedChannelsForUser($user): array {

        $statement = $this->pdo->query("SELECT C.name, C.description FROM Channels C WHERE C.subscribers LIKE '%$user%'");
        return $statement->fetchAll(PDO::FETCH_ASSOC);
    }
}

//$db = new DatabaseUtils();
//$db->subscribeToChannel(1, "ana");
//$db->selectChannelsByPerson("octav");