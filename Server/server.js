// Create node server with websocket
var app = require('http').createServer();
var io = require('socket.io')(app);
app.listen(23712);
