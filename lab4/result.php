<?php
  function generate_sort_link($order_param) {
    $query_string = $_GET;
    $query_string['order_by'] = $order_param;
    return "?" . http_build_query($query_string);
  }

  setcookie("bostadssok", serialize($_GET));

  $ajax_request = !empty($_SERVER['HTTP_X_REQUESTED_WITH']) && strtolower($_SERVER['HTTP_X_REQUESTED_WITH']) == 'xmlhttprequest' ;

  $mysqli = new mysqli("localhost", "root", "", "intnet_lab4");

  if ($mysqli->connect_errno) {
    echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
  }

  $conditions = array();

  if($_GET['lan'] != "") {
    array_push($conditions, "lan = '" . $_GET['lan'] . "'");
  }
  if($_GET['rum'] != "") {
    array_push($conditions, "rum = " . $_GET['rum']);
  }
  if($_GET['objekttyp'] != "") {
    array_push($conditions, "objekttyp = '" . $_GET['objekttyp'] . "'");
  }

  $query_string = "select * from bostader"; 
  if(!empty($conditions)) {
    $query_string = $query_string . " where " . implode(" and ", $conditions);
  }

  if(array_key_exists('order_by', $_GET) && $_GET['order_by'] != "") {
    $query_string = $query_string . " order by " . $_GET['order_by'] . " asc";
  } else {
    $query_string = $query_string . " order by pris asc";
  }

  $query_string = $query_string . ";";

  $search_results = $mysqli->query($query_string);
  
?>

<?php
if(!$ajax_request) {
  echo "<html><head><title>Bostadssökningsresultat</title></head><body>";
}
?>
    <h1>Resultat</h1>

    <table>
      <thead>
        <tr>
          <th>Län</th>
          <th>Objekttyp</th>
          <th>Adress</th>
          <th><a data-value="area" href="<?php echo generate_sort_link('area') ?>">Area</a></th>
          <th><a data-value="rum" href="<?php echo generate_sort_link('rum') ?>">Rum</a></th>
          <th><a data-value="pris" href="<?php echo generate_sort_link('pris') ?>">Pris</a></th>
          <th><a data-value="avgift" href="<?php echo generate_sort_link('avgift') ?>">Avgift</a></th>
        </tr>
      </thead>
      <tbody>
        <?php 
          $search_results->data_seek(0);
          while($row = $search_results->fetch_assoc()) {
            echo "<tr>\n";
            echo "<td>". $row['lan'] ."</td>\n";
            echo "<td>". $row['objekttyp'] ."</td>\n";
            echo "<td>". $row['adress'] ."</td>\n";
            echo "<td>". $row['area'] ."</td>\n";
            echo "<td>". $row['rum'] ."</td>\n";
            echo "<td>". $row['pris'] ."</td>\n";
            echo "<td>". $row['avgift'] ."</td>\n";
            echo "</tr>\n";
          }  
        ?>
      </tbody>
    </table>
<script>
$('a').click(function(){
  $('#order_by').attr('value', $(this).data('value'))
  performAjaxRequest();
  return false;  
});
</script>
<?php
  if(!$ajax_request) {
    echo "</body></html>";
  }
?>

