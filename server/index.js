const http = require('http');
const WebSocket = require('ws');
const uuid = require('uuid')

const server = http.createServer();

const port = process.env.PORT || ""

const wss = new WebSocket.Server({ server });
require('dotenv').config()

var mongoose = require('mongoose');

const uri = process.env.DB_CONNECTION;
mongoose.connect(uri, 
  {  useNewUrlParser: true,  useUnifiedTopology: true})
  .then(() => {  console.log('MongoDB Connected')})
  .catch(err => console.log(err))
  
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
    } else if (obj.type === "makeMove") {
      makemove(obj)
    } else if (obj.type === "getLeaderboard"){
      
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
    const seed = Math.random()
    p1ws.send(JSON.stringify({type:"startGame", player:1, opponentId: p2ws.id, seed}))
    p2ws.send(JSON.stringify({type:"startGame", player:2, opponentId: p1ws.id, seed}))
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
