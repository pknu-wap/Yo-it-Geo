var express = require('express');
var app = express();
var bodyParser = require('body-parser');

app.use(bodyParser.urlencoded({extended:true}));

//mysql불러오기
var mysql = require('mysql');

//mysql커넥션 생성
var connection = mysql.createConnection({
  host: "localhost",
  port: 3306,
  user: "root",//계정 아이디
  password: "1234",//계정 비밀번호
  database: "yoitgeo" //접속할 db
});

//mysql접속
connection.connect();
var sql='select * from geotable';
connection.query(sql,function(err,rows){
    if(err){
        console.log(err);
    }else {
        console.log('rows',rows);
    }
})

app.listen(3000,function(){
console.log('server is running...');
})