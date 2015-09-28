<?php
	require 'DataBaseConnect.php';
	$database = new DataBaseConnect();
?>

<html>
	<head>
		<title>Merchant Details</title>
	</head>
	<body leftmargin="450">
			<img src="img/User.png" /><br>
			Merchant name: McDonalds<br>
			ID: 58<br><br>

		<table border="1">
			<tr>
				<td>Sno</td>
				<td>Customer ID</td>
				<td>Customer Name</td>
				<td>Balance</td>
				<td>Mobile</td>
			</tr>
			<?php
				$result=$database->execute_query_return("select * from `merchant1`");
				while($row = mysql_fetch_array($result)) 
					{
						echo "<tr>";
						echo "<td>".$row['sno']."</td>";
						echo "<td>".$row['cid']."</td>";
						echo "<td>".$row['cname']."</td>";
						echo "<td>".$row['balance']."</td>";
						echo "<td>".$row['mobile']."</td>";
						echo "</tr>";
					}
			?>
		</table>
	</body>
</html>