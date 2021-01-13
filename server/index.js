const express = require('express');
const mysql = require('mysql');
const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '0000',
    database: 'yoitgeo'
});



const app = express();

app.set('port',process.env.PORT || 3000);

app.get('/',(req,res)=>{
    res.send("Root");
});

app.get('/geosite',(req,res)=>{
    connection.query('Select * from geosite',(error,rows)=>{
        if(error) throw errors;
        res.send({data:rows});

    });
});

app.get('/gps_site',(req,res)=>{
    connection.query('select * from gps_site order by geo_name desc',(error,rows)=>{
        if(error) throw errors;
        res.send({data:rows});
    })
})

app.get('/experience_program',(req,res)=>{
    connection.query('select * from experience_program',(error,rows)=>{
        if(error) throw errors;
        res.send({data:rows});
    })
})

app.listen(app.get('port'),()=>{
    console.log('Express server listening on port'+app.get('port'));
});


//connection.connect();
/*
connection.query('SELECT * FROM geosite',(error,rows,fields)=>{
    if(error) throw error;
    console.log('geosite info: ',rows);
});
*/
//connection.end();

