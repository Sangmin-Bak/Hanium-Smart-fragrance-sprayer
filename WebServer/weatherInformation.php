<?php
//	$con = mysqli_connect("127.0.0.1","root","1q2w3e4r!@","android_set");

	$location = fopen("file_location.txt", "r");
	//echo "<center>"</center>
	echo "<center>".fgets($location)."</center>"; echo '<br>';
	fclose($location);
	
	$file_weather = fopen("file_weather.txt", "r");
	$file_weather2 = fopen("file_weather.txt", "r");
	$weather = fread($file_weather, 6);
	$weather2 = fread($file_weather2, 12);

	echo "<center>";
	$temp = fopen("file_temp.txt", "r");
	echo "<font size=10>".fgets($temp); echo  "<font size=10>"."℃ <br>"."</font>";
	fclose($temp);
	
	if($weather == "맑음") {
		echo "<img src='sun.png' border=0 width=120 height=120 align='center'/> <br>";
	}
	else if($weather == "흐림")  {
		echo "<img src='cloudy.png' border=0 width=120 height=120 align='center'/> <br>";
	}
	else if($weather == "비옴") {
		echo "<img src='rain.png' border=0 width=120 height=120 align='center'/> <br>";
	}
	else if($weather2 == "구름많음") {
		echo "<img src='cloud.png' border=0 width=120 height=120 align='center'/> <br>";
	}	

	$total_temp = fopen("file_total_temp.txt", "r");
	echo "<font size=4>".fgets($total_temp)."</font>"; echo " ";
	fclose($total_temp);

	if($weather2 == "구름많음") {
		echo "<font size=4>".nl2br($weather2)."</font>"; echo '<br>';
	}
	else {
		echo "<font size=4>".nl2br($weather)."</fong>"; echo '<br>';
	}
	fclose($weather); fclose($weather2);
	
?>

