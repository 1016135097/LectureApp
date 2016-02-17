<?php
 
require_once 'FunctionSet.php';

/*---------------------------------魔术代码-----------------------------------------*/
if (get_magic_quotes_gpc())
{
	$process = array(&$_GET, &$_POST, &$_COOKIE, &$_REQUEST);
	while (list($key, $val) = each($process))
	{
		foreach ($val as $k => $v)
		{
			unset($process[$key][$k]);
			if (is_array($v))
			{
				$process[$key][stripslashes($k)] = $v;
				$process[] = &$process[$key][stripslashes($k)];
			}
			else
			{
				$process[$key][stripslashes($k)] = stripslashes($v);
			}
		}
	}
	unset($process);
}

/*--------------------------------------信息接收-------------------------------------------*/
$Title=$_POST['Title'];
$LectureCont=$_POST['LectureCont'];
$LectureTime=$_POST['LectureTime'];
$LecturePlace=$_POST['LecturePlace'];
$Others=$_POST['Others'];

/*--------------------------------------上传讲座信息---------------------------------------*/
//数据库连接
$dsn = 'mysql:host=localhost;dbname=lectureapp';
$user = 'javamanager';
$password = 'admin123';

try
{
	$pdo = new PDO($dsn, $user, $password);
	$pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	$pdo->exec('SET NAMES "utf8"');
}
catch (PDOException $e)
{
	$error1 = 'Unable to connect to the database server.';
	ErrorNotice();
	echo $error1;
	exit();
}

//讲座信息接收及提交
try
{
	$sql = 'INSERT INTO lecture SET
        Title=:Title,
		LectureCont=:LectureCont,
		LectureTime=:LectureTime,
		LecturePlace=:LecturePlace,
		Others=:Others,
		SignUpNum=:SignUpNum,
		CheckInNum=:CheckInNum';
	$s = $pdo->prepare($sql);
	$s->bindValue(':Title', $Title);   
	$s->bindValue(':LectureCont', $LectureCont);
	$s->bindValue(':LectureTime', $LectureTime);
	$s->bindValue(':LecturePlace', $LecturePlace);
	$s->bindValue(':Others', $Others);
	$s->bindValue(':SignUpNum', '0');
	$s->bindValue(':CheckInNum', '0');
	$s->execute();
}
catch (PDOException $e)
{
	$error2 = 'Can‘t Post data to the database!';
	ErrorNotice();
	echo $error2;
	exit();
}

$ID=$pdo->lastInsertId();
echo $ID;
?>
