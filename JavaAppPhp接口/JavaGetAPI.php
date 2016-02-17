<?php 
/*---------------------------------ħ������-----------------------------------------*/
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

/*-----------------------------------------�û��鼮��Ϣ��ȡ--------------------------------------------*/

$idLength=$_POST['idLength'];
//$idLength='7';
//���ݿ�����
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
//��ȡ���ݿ�������ID
try {
	$sql='SELECT id FROM lecture';
	$result = $pdo->query($sql);
} catch (PDOException $e) {
	$error = 'Error Fetching!';
	echo $error;
	exit();
}

//���������ȡ��ID
foreach ($result as $row){
	$idNum[] = $row['id'];
}

//��ѯ���ݿ��Ի�ȡ��Ϣ

	try {
		$sql='SELECT * FROM lecture WHERE id=:sendId;';
		$s = $pdo->prepare($sql);
		$s->bindValue(':sendId', $idNum[$idLength-1]);
		$s->execute();
		$result=$s->fetchAll(PDO::FETCH_ASSOC);
	} catch (PDOException $e) {
		$error = 'Error Fetching!';
		echo $error;
		exit();
	}
	
	//���������ȡ����Ϣ
	foreach ($result as $row){
		$Id=$row['id'];
		$Title=$row['Title'];
		$LectureCont=$row['LectureCont'];
		$LectureTime=$row['LectureTime'];
		$LecturePlace=$row['LecturePlace'];
		$Others=$row['Others'];
	}
	/*---------------------------------------------���������Ϣ���ͻ���---------------------------*/

		$LectureInf=array(
			'Id'=>$Id,
			'Title'=>$Title,
			'LectureCont'=>$LectureCont,
			'LectureTime'=>$LectureTime,
			'LecturePlace'=>$LecturePlace,
			'Others'=>$Others,
		);
		echo json_encode($LectureInf,JSON_UNESCAPED_UNICODE);

?>