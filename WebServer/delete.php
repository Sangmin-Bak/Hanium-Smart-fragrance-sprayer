<?php
	$con = mysqli_connect("127.0.0.1","root","","spray");

	if(mysqli_query($con, "DELETE FROM motor"))
		echo "Table Delete Success";
	else
		echo "fali";
	
	mysqli_close($con);
?>