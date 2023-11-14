const express = require('express')
const socketIO = require('socket.io')

const app = express()
const server = require('http').createServer(app)
const io = socketIO(server)

io.on('connection', (socket)=>{
    const VAIid = socket.id
    console.log(`${VAIid} client connected`);
    socket.on('sendMessage', (data) => {
        console.log(`Message received from client: ${data}`);
        io.emit('receiveMessage',{
            message : data,
            id : VAIid
        })
    })

})

server.listen(3000,() => {
    console.log('Server listening on port 3000');
})

