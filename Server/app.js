 var express = require('express');
 var http = require('http');
 var app = express();


 app.get('/',function(req,res){
   res.send('Hello /');
 })
 app.get('/world.html',function(req,res){
   res.send('Hello World!');
 });



//mysql불러오기
var mysql = require('mysql');

//mysql커넥션 생성
var connection = mysql.createConnection({
  host: 'localhost',
  port: 3306,
  user: "root",//계정 아이디
  password: "1234",//계정 비밀번호
  database: "yoitgeo" //접속할 db
});

//mysql접속
connection.connect();

app.post('/user',function(req,res){
  var attr_id=req.bodt.attr_id;
  var 
}
  )

connection.end();

app.listen(3000,function(){
  console.log('Express server listening on post ');
});