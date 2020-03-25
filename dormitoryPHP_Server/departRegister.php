<?php
    include_once("connect.php");
    $registerDate=$_POST['registerDate'];//APP post过来的提交时间
    $ID=$_POST['ID'];//APP post过来的学生ID
    $dormID=$_POST['dormID'];//APP post过来的宿舍号
    $departCause=$_POST['departCause'];//APP post过来的离宿原因
    $departTime=$_POST['departTime'];//APP post过来的离宿时间
    $backTime=$_POST['backTime'];//APP post过来的返宿时间
    $contact=$_POST['contact'];//APP post过来的联系方式
    $belong=$_POST['belong'];//APP post过来的宿舍楼
    $result=mysqli_query($conn,"INSERT INTO departinfo(registerDate,ID,dormID,departCause,departTime,backTime,contact,belong) VALUES('$registerDate','$ID','$dormID','$departCause','$departTime','$backTime','$contact','$belong')");
     if($result){
       $back['status']="10";
       $back['info']="commit success";
       echo (json_encode($back));
    }else{
       $back['status']="-13";
       $back['info']="commit fail";
       echo (json_encode($back));
    }
    mysqli_close($conn);
 ?>