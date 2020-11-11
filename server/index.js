const http = require('http');
const WebSocket = require('ws');
const uuid = require('uuid')

const server = https.createServer({
  cert: fs.readFileSync('/path/to/cert.pem'),
  key: fs.readFileSync('/path/to/key.pem')
});

const wss = new WebSocket.Server({ server });

const matchMakingQueues = {}
const matches = {}
const players = {}

wss.on('connection', function connection(ws) {
  ws.id = uuid.v4();
  players[ws.id] = ws
  console.log(ws.id)
  ws.on('message', function incoming(message) {
    obj = JSON.parse(message)
    if (obj.type === "matchmaking"){
      matchmaking(ws, obj)
    } else if (obj.type === 'makeMove') {
      makemove(obj)
    }
  });
  // ws.send('receiveMove');
});

function matchmaking(ws) {
  if (matchMakingQueues[obj.gameMode] === undefined) {
    matchMakingQueues[obj.gameMode] = []
  }
  const queue = matchMakingQueues[obj.gameMode]
  queue.push(ws)
  if (queue.length == 2) {
    const p1ws = queue[0]
    const p2ws = queue[1]
    p1ws.send(JSON.stringify({type:"startGame", player:1, opponentId: p2ws.id}))
    p2ws.send(JSON.stringify({type:"startGame", player:2, opponentId: p1ws.id}))
    matches[p1ws] = p2ws.id
    matches[p2ws] = p1ws.id
    queue.length = 0
  }
}

function makemove(obj) {
  const msg = {type: "receiveMove", move: obj.move, opponentId: obj.opponentId}
  const wsToSend = players[obj.opponentId]
  wsToSend.send(JSON.stringify(msg));
}

server.listen(8080);
