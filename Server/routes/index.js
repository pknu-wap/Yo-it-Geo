var express = require('express');
var app = express();
var bodyParser = require('body-parser');

app.use(bodyParser.urlencoded({extended:true}));

//mysql불러오기
var mysql = require('mysql');

//mysql커넥션 생성
var connection = mysql.createConnection({
  host: "172.30.1.37",
  port: 3306,
  user: "root",//계정 아이디
  password: "1234",//계정 비밀번호
  database: "yoitgeo" //접속할 db
});

//mysql접속
connection.connect();

//user라우터
app.post('../index.html',function(req,res){

  var attr_id = req.body.attr_id;
  var geo_id = req.body.geo_id;
  var name = req.body.name;
  var comm = req.body.comm;

  //SQL문 실행
  connection.query("INSERT INTO geotable (attr_id,geo_id,name,comm) VALUES ('"+attr_id+"','"+geo_id+"','"+name+"',
'"+comm+"')"
function(error,result,fields){

  if(error){ //에러 발생 시
    res.send('err : '+ error)
  }else { //실행 성공
    console.log(attr_id+','+geo_id+','+name+','+comm);
    res.send('success create geotable elements: '+attr_id+','+geo_id+','+name+','+comm);
  }
});

})
