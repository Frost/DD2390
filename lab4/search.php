<?php
  $stored_search_params = unserialize($_COOKIE['bostadssok']);
  
  $mysqli = new mysqli("localhost", "root", "", "intnet_lab4");

  if ($mysqli->connect_errno) {
    echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
  }

  $county = $mysqli->query("select distinct lan from bostader");
  $no_rooms = $mysqli->query("select distinct rum from bostader");
  $object_type = $mysqli->query("select distinct objekttyp from bostader");

  $order_param = $_GET['order_by']

?>

<html>
  <head>
    <title>Bostadssök</title>
    <script type="text/javascript" src="jquery.js"></script>
  </head>
  <body>
    <h1>Bostadssök</h1>
    <div>
      <form action="result.php" method="get" id="search_form">
        <input type="hidden" id="order_by" name="order_by" value="<?php echo($order_param) ?>">
        <p>
          <label for="lan"><span>Län</span>
            <select id="lan" name="lan">
              <option value=""></option>
              <?php 
                $county->data_seek(0);
                while($row = $county->fetch_assoc()) {
                  if($stored_search_params['lan'] == $row['lan']) {
                    echo "<option value=\"". $row["lan"] ."\" selected=\"selected\">". $row["lan"] ."</option>";
                  } else {
                    echo "<option value=\"". $row["lan"] ."\">". $row["lan"] ."</option>";
                  }
                }
              ?>
            </select>
          </label>
        </p>
        <p>
          <label for="rum"><span>Antal rum</span>
            <select id="rum" name="rum">
              <option value=""></option>
              <?php 
                $no_rooms->data_seek(0);
                while($row = $no_rooms->fetch_assoc()) {
                  if($stored_search_params['rum'] == $row['rum']) {
                    echo "<option value=\"". $row["rum"] ."\" selected=\"selected\">". $row["rum"] ."</option>";
                  } else {
                    echo "<option value=\"". $row["rum"] ."\">". $row["rum"] ."</option>";
                  }
                }
              ?>
            </select>
          </label>
        </p>
        <p>
          <label for="objekttyp"><span>Objekttyp</span>
            <select id="objekttyp" name="objekttyp">
              <option value=""></option>
              <?php 
                $object_type->data_seek(0);
                while($row = $object_type->fetch_assoc()) {
                  if($stored_search_params['objekttyp'] == $row['objekttyp']) {
                    echo "<option value=\"". $row["objekttyp"] ."\" selected=\"selected\">". $row["objekttyp"] ."</option>";
                  } else {
                    echo "<option value=\"". $row["objekttyp"] ."\">". $row["objekttyp"] ."</option>";
                  }
                }
              ?>
            </select>
          </label>
        </p>

        <input type="submit" value="Sök" id="search">
      </form>
    </div>
    <div id="search_results"></div>
    <script type="text/javascript">
      var performAjaxRequest = function() {
        $.ajax({
          type: "GET",
          url: "result.php",
          data: $('#search_form').serialize(),
          success: function (data) {
            $('#search_results').html(data);
          }
        });
      };
      $('#search').click(function(){
        performAjaxRequest();
        return false;
      });
      performAjaxRequest();

    </script>
  </body>
</html>

<?php
  $mysqli->close();
?>

