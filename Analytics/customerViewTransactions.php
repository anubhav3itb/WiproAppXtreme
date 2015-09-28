<?php
	require 'DataBaseConnect.php';
	$database = new DataBaseConnect();
?>

<html>
	<head>
		<title>View Customer Transactions</title>
	</head>
	<body>
		<table border="1">
			<tr>
				<td>Sno</td>
				<td>Customer ID</td>
				<td>Merchant ID</td>
				<td>Age</td>
				<td>Sex</td>
				<td>Amount</td>
				<td>Time</td>
				<td>Date</td>
			</tr>
			<?php
				$result=$database->execute_query_return("select * from `transactions` where cid=805707");
				while($row = mysql_fetch_array($result)) 
					{
						echo "<tr>";
						echo "<td>".$row['sno']."</td>";
						echo "<td>".$row['cid']."</td>";
						echo "<td>".$row['mid']."</td>";
						echo "<td>".$row['age']."</td>";
						echo "<td>".$row['sex']."</td>";
						echo "<td>".$row['amount']."</td>";
						echo "<td>".$row['time']."</td>";
						echo "<td>".$row['date']."</td>";
						echo "</tr>";
					}
			?>
		</table>
	</body>
</html>