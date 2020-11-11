const http = require('http');
const WebSocket = require('ws');
const uuid = require('uuid')

const server = http.createServer();
const wss = new WebSocket.Server({ server });

const matchMaking = []
const matches = {}
const players = {}

wss.on('connection', function connection(ws) {
  ws.id = uuid.v4();
  players[ws.id] = ws
  console.log(ws.id)
  ws.on('message', function incoming(message) {
    obj = JSON.parse(message)
    if (obj.type === "matchmaking"){
      matchmaking(ws)
    } else if (obj.type === 'makeMove') {
      makemove(obj)
    }
  });
  // ws.send('receiveMove');
});

function matchmaking(ws) {
  matchMaking.push(ws)
  if (matchMaking.length == 2) {
    matchMaking[0].send(JSON.stringify({type:"startGame", player:1, opponentId: matchMaking[1].id}))
    matchMaking[1].send(JSON.stringify({type:"startGame", player:2, opponentId: matchMaking[0].id}))
    console.log(matchMaking[1].id)
    console.log(matchMaking[0].id)
    matchMaking.length = 0
  }
}

function makemove(obj) {
  const move = obj.move
  console.log("here2")
  const msg = {type: "receiveMove", move: 0, opponentId: obj.opponentId}
  const wsToSend = players[obj.opponentId]
  wsToSend.send(JSON.stringify(msg));
}

server.listen(8080);
