<?php
    include_once("connect.php");
    $Rtime=$_POST['Rtime'];//APP post������ǩ������
    $houseparentID=$_POST['houseparentID'];//APP post�������޹ܺ�
    $title=$_POST['title'];//APP post������ǩ������
    $govern=$_POST['govern'];//APP post�������޹ܹ����¥��
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