// Create node server with websocket
var app = require('http').createServer();
var io = require('socket.io')(app);
app.listen(23712);


// Websocket server to run in background
io.of('/central').on('connection', function(socket) {
    var joinedRoom = null;

    // Edtech start the classroom
    socket.on('edtech-classroom-start-request', function(load) {
        console.log('ffff');
        var info = JSON.parse(load);
        socket.join(info.room_id);
        console.log('joined room '+ info.room_id);
        joinedRoom = info.room_id;

        // Respond to sender
        socket.emit(
            'edtech-classroom-start-response',
            JSON.stringify({
                msg     : 'Succesfully joined room '+info.room_id,
            })
        );

        // Inform other room members that he is joining
        socket.broadcast.to(info.room_id).send(
            JSON.stringify ({
                type  : 'edtech-classroom-start-response',
                msg   : 'Someone joined room '+info.room_id,
            })
        );
    });

    // Edtech send the message
    socket.on('edtech-classroom-chat-send', function(load) {
        // socket.broadcast.emit('edtech-classroom-chat-response', load);
        console.log('Server received signal '+load);
        socket.broadcast.emit('message', load);
        // socket.emit('message', load);

        // if (joinedRoom) {
        //     var info = JSON.parse(load);
        //     console.log(info);

        //     // socket.broadcast.emit('message', "this is a test");
        //     socket.broadcast.to(info.room_id).send(
        //         JSON.stringify({
        //             type    : 'edtech-classroom-chat-response',
        //             content : info.content
        //         })
        //     );
        // } else {
        //     console.log('User is not in a proper room');
        // }
    });

});