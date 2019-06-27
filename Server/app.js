var express = require('express');
var app = express();
var url=require('url');

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

var app =http.createServer(function(request,response){
    var _url=request.url;
    var queryData = url.parse(_url,true).query;
    var pathname=url.parse(_url,true).pathname;
    if(pathname === '/'){
        if(queryData.name!=undefined){
            
        }
    }
})
app.get('/:name',function(req,res,next){
var sql='select geo_id,name,comm from geotable where name=?';
var request_name;
connection.query(sql,request_name,function(err,rows){
    if(err){
        console.log(err);
    }else {
        console.log('rows',rows);
    }
})

});

app.listen(3000,function(){
console.log('server is running...');
})

