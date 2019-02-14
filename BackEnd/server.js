const bodyParser = require('body-parser');
const express = require('express');
const fs = require('fs'); 
const app = express();

var quaternion;

console.log('Server starting ...');
app.listen(80, () => {
  console.log('Server started!');
});
app.use(bodyParser.json());
app.use(express.static('public'))

app.route('/').get((req, res) => {
  fs.readFile('index.html', function(err, data) {
    res.writeHead(200, {'Content-Type': 'text/html'});
    res.write(data);
    res.end();
  });
});

//app.route('/js/three.js').get((req, res) => {
//  fs.readFile('./js/three.js', function(err, data) {
//    res.writeHead(200, {'Content-Type': 'text/html'});
//    res.write(data);
//    res.end();
//  });
//});

app.route('/api/rotation').get((req, res) => {
  res.status(200).send(quaternion);
  console.log(quaternion);
});

app.route('/api/rotation').post((req, res) => {
  quaternion = req.body;
  res.status(200).send(quaternion);
  //console.log(quaternion);
});
