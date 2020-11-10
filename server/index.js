const http = require('http');
const WebSocket = require('ws');
const uuid = require('uuid')

const server = http.createServer();
const wss = new WebSocket.Server({ server });

const arr = []
const matches = {}

wss.on('connection', function connection(ws) {
  ws.id = uuid.v4();
  console.log(ws.id)
  ws.on('message', function incoming(message) {
    obj = JSON.parse(message)
    console.log(obj)
    if (obj.type === "matchmaking"){
      matchmaking(ws)
    } else if (obj.type === 'makeMove') {
      makemove(obj)
    }
  });
  // ws.send('receiveMove');
});

function matchmaking(ws) {
  arr.push(ws)
    if (arr.length == 2) {
      arr[0].send(JSON.stringify({type:"startGame", player:1}))
      arr[1].send(JSON.stringify({type:"startGame", player:2}))
    }
}

function makemove() {
  
}

server.listen(8080);
