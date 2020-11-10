const WebSocket = require('ws');

const ws = new WebSocket('ws://localhost:8080');

ws.on('open', function open() {
  msg = {type: "matchmaking"}
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
    console.log(obj.player)
    if (obj.player == 1) {
      msg = {type: "makeMove", move: 0}
    }
}

function receiveMove(obj) {
  console.log("Received Move")
  console.log(obj.move)
  msg = {type: "makeMove", move: 0}
}