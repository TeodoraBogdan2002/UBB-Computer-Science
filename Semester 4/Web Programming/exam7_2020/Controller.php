<?php

require_once 'DatabaseUtils.php';
class Controller {
    private DatabaseUtils $databaseUtils;

    public function __construct() {
        $this->databaseUtils = new DatabaseUtils();
    }

    public function run() {
        if (isset($_GET['action']) && !empty($_GET['action'])) {
            switch ($_GET['action']) {
                case 'getAllWebsites':
                    $this->getAllWebsites();
                    break;
                case 'update':
                    $this->update($_GET["id"],$_GET["k1"],$_GET["k2"],$_GET["k3"],$_GET["k4"],$_GET["k5"]);
                    break;
                case 'getWith3Keys':
                    $keywords = $_GET['keywords'];
                    $this->getWith3Keys($keywords);
                    break;
            }
        }
    }

    private function getAllWebsites() {
        $websites = $this->databaseUtils->getAllWebsites();
        foreach ($websites as &$website) {
            $count = $this->databaseUtils->getDocumentsOfWebsite($website["id"]);
            foreach ($count as $key => $value) {
                $website["numberOfDocuments"] = $value["COUNT(*)"];

            }
        }
        echo json_encode($websites);
    }

    private function update($doc, $k1, $k2, $k3, $k4, $k5) {
        echo $doc;
        $this->databaseUtils->updateDocument($doc, $k1, $k2, $k3, $k4, $k5);
    }

    private function getWith3Keys($keywords) {
        $keywords_array = explode(" ", $keywords);
        $all_docs = $this->databaseUtils->getAllDocuments();
        $final_array = array();
        foreach ($all_docs as $doc) {
            $count = 0;
            foreach ($keywords_array as $keyword) {
                if ($doc["keyword1"] == $keyword) {
                    $count += 1;
                }
                if ($doc["keyword2"] == $keyword) {
                    $count += 1;
                }
                if ($doc["keyword3"] == $keyword) {
                    $count += 1;
                }
                if ($doc["keyword4"] == $keyword) {
                    $count += 1;
                }
                if ($doc["keyword5"] == $keyword) {
                    $count += 1;
                }
            }
            if ($count === 3) {
                array_push($final_array, $doc);
            }
        }
        echo json_encode($final_array);

    }
}

$controller = new Controller();
$controller->run();
