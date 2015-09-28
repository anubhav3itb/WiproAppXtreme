<?php

define('DB_HOST','localhost');
define('DB_USER_NAME','root');
define('DB_USER_PASSWORD','');
define ('DB_NAME','brainwaves');

class DataBaseConnect
{
	private $connection;

	function execute_query_return($query)
	{	
		$connection=$this->get_connection();
		mysql_select_db(DB_NAME,$connection);
		return mysql_query($query,$connection);
	}
	
	function get_connection()
	{
		if(is_resource($this->connection))
		{
			return  $this->connection;
		}
		else 
		{
			$this->connection = mysql_connect(DB_HOST,DB_USER_NAME,DB_USER_PASSWORD);
			if(!$this->connection)
			{
				die(mysql_error());	
			}
			else
			{
				return  $this->connection;
			}
		}
	}
	
	function execute_query_update($query) 
	{
		$connection=$this->get_connection();
		mysql_select_db(DB_NAME,$connection);
		if(mysql_query($query,$connection))
		{
			return "done";		
		}
		else
		{
			die(mysql_error());
		}
		
	}
	
	function close_connection() 
	{
		mysql_close($this->connection);
	}
}

?>