<?php 
    $s = mysqli_connect("localhost", "root", "", "test") or die ("실패입니다.");
    print "성공입니다.";
    mysql_close($s);
 ?>
