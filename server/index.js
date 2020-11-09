const http = require('http');
const WebSocket = require('ws');
const uuid = require('uuid')

const server = http.createServer();
const wss = new WebSocket.Server({ server });

wss.on('connection', function connection(ws) {
  ws.id = uuid.v4();
  console.log(ws.id)
  ws.on('matchmaking', function incoming(message) {
    console.log('received: %s', message);
  });
  ws.on('makeMove', function incoming(message) {
    console.log('received: %s', message);
  });
  ws.send('startGame');
  ws.send('receiveMove');
});

server.listen(8080);