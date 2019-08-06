<?php
	$con = mysqli_connect("127.0.0.1","root","1q2w3e4r!@","control");

	if(mysqli_query($con, "DELETE FROM motor"))
		echo "Table Delete Success";
	else
		echo "fali";
	
	mysqli_close($con);
?>