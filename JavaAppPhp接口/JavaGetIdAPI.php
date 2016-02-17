<?php 
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

/*-----------------------------------------用户书籍信息获取--------------------------------------------*/

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
	$error = 'Unable to connect to the database server.';
	echo $error;
	exit();
}
//获取数据库中所有ID
try {
	$sql='SELECT id FROM lecture';
	$result = $pdo->query($sql);
} catch (PDOException $e) {
	$error = 'Error Fetching!';
	echo $error;
	exit();
}

//遍历输出获取的ID
foreach ($result as $row){
	$idNum[] = $row['id'];
}
$IDLength=count($idNum);

	/*---------------------------------------------返回ID数量到客户端---------------------------*/
	
		$LectureInf=array(
			'IDLength'=>$IDLength
		);
		echo json_encode($LectureInf,JSON_UNESCAPED_UNICODE);

?>