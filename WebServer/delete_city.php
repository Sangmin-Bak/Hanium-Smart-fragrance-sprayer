<?php
	$con = mysqli_connect("127.0.0.1","root","1q2w3e4r!@","android_set");

	if(mysqli_query($con, "DELETE FROM area"))
		echo "Table Delete Success";
	else
		echo "fali";
	
	mysqli_close($con);
?>
