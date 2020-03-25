<?php
    include_once("connect.php");
    $registerDate=$_POST['registerDate'];//APP post过来的提交时间
    $ID=$_POST['ID'];//APP post过来的学生ID
    $dormID=$_POST['dormID'];//APP post过来的宿舍号
    $startDate=$_POST['startDate'];//APP post过来的留宿开始时间
    $endDate=$_POST['endDate'];//APP post过来的留宿结束时间
    $contact=$_POST['contact'];//APP post过来的联系方式
    $belong=$_POST['belong'];//APP post过来的宿舍楼
    $result=mysqli_query($conn,"INSERT INTO stayinfo(registerDate,ID,dormID,startDate,endDate,contact,belong) VALUES('$registerDate','$ID','$dormID','$startDate','$endDate','$contact','$belong')");
     if($result){
       $back['status']="11";
       $back['info']="commit success";
       echo (json_encode($back));
    }else{
       $back['status']="-14";
       $back['info']="commit fail";
       echo (json_encode($back));
    }
    mysqli_close($conn);
 ?>