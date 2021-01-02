const WebSocket = require('ws');

const ws = new WebSocket('ws://207.246.87.201:8080');
let opponentId = null

ws.on('open', function open() {
  msg = {type: "matchmaking", gameMode: "Standard"}
  ws.send(JSON.stringify(msg));
});

ws.on('message', function incoming(data) {
  obj = JSON.parse(data)
  if (obj.type === "startGame") {
    startGame(obj)
  } else if (obj.type === "receiveMove") {
    receiveMove(obj)
  }
});

function startGame(obj) {
  console.log("Starting game")
  opponentId = obj.opponentId
  if (obj.player == 1) {
    console.log("here")
    msg = {type: "makeMove", move: 0, opponentId: opponentId}
    ws.send(JSON.stringify(msg));
  }
}

function receiveMove(obj) {
  console.log("Received Move")
  // console.log(obj.move)
  msg = {type: "makeMove", move: 0, opponentId: opponentId}
  ws.send(JSON.stringify(msg));
}