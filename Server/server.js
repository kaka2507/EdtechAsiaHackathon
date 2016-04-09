// Create node server with websocket
var app = require('http').createServer();
var io = require('socket.io')(app);
app.listen(23712);


// Websocket server to run in background
io.of('/central').on('connection', function(socket) {
    var joinedRoom = null;

    socket.on('test', function(load) {
        console.log('gggg');
        console.log(load);
    });

    // Edtech start the classroom
    socket.on('edtech-classroom-start-request', function(load) {
        var info = JSON.parse(load);

        joinedRoom = info.room_id;
        socket.join(info.room_id);

        socket.emit(
            'edtech-classroom-start-response',
            JSON.stringify({
                status  : true,
                role    : 'guest',
                msg     : 'Succesfully joined the room',
            })
        );

        // Inform other room members that he is joining
        socket.broadcast.to(info.room_id).send(
            JSON.stringify ({
                type  : 'edtech-classroom-start-response',
                status: true,
                msg   : {
                    from    : 'guest',
                    action  : 'join',
                }
            })
        );
    });

    // Edtech send the message
    socket.on('edtech-classroom-chat-send', function(load) {
        if (joinedRoom) {
            var info = JSON.parse(load);
            socket.broadcast.to(info.room_id).send(
                JSON.stringify({
                    type  : 'edtech-classroom-response',
                    status: true,
                    msg   : {
                        text : info.text,
                    }
                })
            );
        } else {
            console.log('User is not in a proper room');
        }
    });
});