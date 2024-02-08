<?php

class DatabaseUtils {
    private string $host = 'localhost';
    private string $database = 'exam7';
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

    public function getAllWebsites(): array {
        $statement = $this->pdo->query("SELECT * FROM Websites");
        return $statement->fetchAll(PDO::FETCH_ASSOC);
    }

    public function getDocumentsOfWebsite($websiteId): array {
        $statement = $this->pdo->query("SELECT COUNT(*) FROM Documents D WHERE D.idWebSite ="."$websiteId");
        return $statement->fetchAll(PDO::FETCH_ASSOC);
    }

    public function updateDocument($document, $key1, $key2, $key3, $key4, $key5) {
        $this->pdo->exec("UPDATE Documents SET keyword1 = '$key1', keyword2 = '$key2', keyword3 = '$key3', keyword4 = '$key4', keyword5 = '$key5' WHERE id="."$document");
    }

    public function getAllDocuments(): array {
        $statement = $this->pdo->query("SELECT * FROM Documents");
        return $statement->fetchAll(PDO::FETCH_ASSOC);
    }
//    public function selectChannelsByPerson($person): array {
//        $statement = $this->pdo->query("SELECT * FROM Channels C WHERE C.ownerid = (SELECT id FROM Persons P WHERE P.name = '$person')");
//        return $statement->fetchAll(PDO::FETCH_ASSOC);
//    }

//      $this->pdo->exec(""); -- update / add /delete
}
