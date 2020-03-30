<?php
    include_once("connect.php");
    $dorm=$_POST['dorm'];//APP post过来的宿舍楼号
    $waterUrl=$_POST['waterUrl'];//APP post过来的水费网址
    $electricityUrl=$_POST['electricityUrl'];//APP post过来的电费网址
    $sql = "INSERT INTO waterandelectricity VALUES('$dorm','$waterUrl','$electricityUrl') ON DUPLICATE KEY UPDATE";
    if($waterUrl&&$waterUrl!="" && $electricityUrl&&$electricityUrl != ""){
        $sql = $sql . " waterUrl='$waterUrl', electricityUrl='$electricityUrl'";
    }else{
        if(!$water&&$waterUrl==""){
            $sql  = $sql . " electricityUrl='$electricityUrl'";
        }else if(!$electricityUrl&&$electricityUrl==""){
            $sql = $sql . " waterUrl='$waterUrl'";
        }
    }
    $result=mysqli_query($conn,$sql);
    if($result){
        $back['status']="18";
        $back['info']="set water and electricity successfully";
        echo (json_encode($back));
    }else{
        $back['status']="-24";
        $back['info']="failed to set water and electricity";
        echo (json_encode($back));
    }
    mysqli_close($conn);
 ?>   