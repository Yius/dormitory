<?php
    include_once("connect.php");
    $Rtime=$_POST['Rtime'];//APP post过来的签到日期
    $houseparentID=$_POST['houseparentID'];//APP post过来的宿管号
    $title=$_POST['title'];//APP post过来的签到标题
    $govern=$_POST['govern'];//APP post过来的宿管管理的楼号
    $sql=mysqli_query($conn,"SELECT COUNT(*) as 'totalnums' FROM userinfo WHERE belong LIKE '$govern'");
    $totalnums=0;
    if($result=mysqli_fetch_assoc($sql)){
        $totalnums=$result['totalnums'];
    }
    $result=mysqli_query($conn,"INSERT INTO signrecord (Rtime,houseparentID,title,govern,totalnums) VALUES('$Rtime','$houseparentID','$title','$govern','$totalnums')");
     if($result){
       $back['status']="13";
       $back['info']="start a new sign success";
       echo (json_encode($back));
    }else{
       $back['status']="-16";
       $back['info']="start a new sign fail";
       echo (json_encode($back));
    }
    mysqli_close($conn);
 ?>