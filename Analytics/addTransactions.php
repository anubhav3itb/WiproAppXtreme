<?php
	function splitString($str){
		echo $str;
		$tok = strtok($string, "#");
		$args=array();
		echo $str;
		while ($tok !== false) {
			$args+=$tok;
			echo $tok;
		    $tok = strtok("#");
		}
		return $args;
	}
?>

<?php
	require 'DataBaseConnect.php';
	$database = new DataBaseConnect();
	// $mid = rand(10,100);
	// $cid = rand(100000,1000000);
	// $amount = rand(1,10000);
	// $age = rand(15,90);
	// $sex = rand(1,2);
	date_default_timezone_set('Asia/Kolkata');
	$time = (string)date("H:i");  
	$date = date("d/m/Y"); 	
	// $database->execute_query_update("INSERT INTO `transactions`(`cid`, `mid`, `age`, `sex`, `amount`, `time`, `date`) VALUES (".$cid.",".$mid.",".$age.",".$sex.",".$amount.",'".$time."','".$date."')");
	try{
		$mid = $_GET['mid'];
		$cid = $_GET['cid'];
		$amount = $_GET['amount'];
		$age = $_GET['age'];
		$sex = $_GET['sex'];
		$database->execute_query_update("INSERT INTO `transactions`(`cid`, `mid`, `age`, `sex`, `amount`, `time`, `date`) VALUES (".$cid.",".$mid.",".$age.",".$sex.",".$amount.",'".$time."','".$date."')");
		$result=$database->execute_query_return("select * from `merchant1` where cid=".$cid);
		$row = mysql_fetch_array($result);
		$newBalance = $row['balance'] + $amount;
		echo $row['balance']." ".$amount;
		$database->execute_query_update("UPDATE `merchant1` SET `balance` = ".$newBalance." where `cid`=".$cid);
	}catch (Exception $e) {
   		 echo 'Caught exception: ',  $e->getMessage(), "\n";
	}
	
?>