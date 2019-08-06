<!DOCTYPE html>
 <html>
 <head>
 <title>led</title>
 </head>
 <body>
	 <h1>설정된 향</h1>
 </form>
 </body>
 </html>

<?php
	$con = mysqli_connect("127.0.0.1","root","1q2w3e4r!@","android_set");

	$fragrance_set = mysqli_query($con, "SELECT * FROM fragrance_set");
	$fragrance = mysqli_fetch_array($fragrance_set);
	
	$time_set = mysqli_query($con, "SELECT * FROM time_set");
	$time = mysqli_fetch_array($time_set);

	echo "맑음 향 : "; echo $fragrance[0]; echo ', ';
	echo "흐림 향 : "; echo $fragrance[1]; echo ', ';
	echo "비옴 향 : "; echo $fragrance[2]; echo '<br>';
	
	echo "다음 분사 시간 : "; echo $time[0]; echo "시 "; echo $time[1]; echo "분";

//	mysqli_close($con);
//	echo json_encode($LedStatus);
?>
