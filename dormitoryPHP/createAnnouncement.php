<?php
    include_once("connect.php");
    $Atime=$_POST['Atime'];//APP post过来的发布日期
    $houseparentID=$_POST['houseparentID'];//APP post过来的宿管号
    $govern=$_POST['govern'];//APP post过来的宿管所管楼号
    $content=$_POST['content'];//APP post过来的发布内容
    $title=$_POST['title'];//APP post过来的发布标题
    $result=mysqli_query($conn,"INSERT INTO announcement (Atime,houseparentID,content,title,govern) VALUES('$Atime','$houseparentID','$content','$title','$govern')");
     if($result){
       $back['status']="7";
       $back['info']="announce success";
       echo (json_encode($back));
    }else{
       $back['status']="-10";
       $back['info']="announce fail";
       echo (json_encode($back));
    }
    mysqli_close($conn);
 ?>