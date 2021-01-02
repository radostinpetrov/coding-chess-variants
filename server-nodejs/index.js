const http = require('http');
const WebSocket = require('ws');
const uuid = require('uuid')

const server = http.createServer();

const port = process.env.PORT || ""

const Player = require("./models/Player")

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
    console.log(obj)
    if (obj.username) {
      Player.findOne({username: obj.username}, (err, player) => {
        if (err) {
          console.error(err)
          return
        }
        if (!player) {
          const new_player = new Player({username: obj.username})
          new_player.save().catch(err => console.err(err))
        }
      })
    }
    
    if (obj.type === "matchmaking"){
      // Check if user is in DB and if not then create user in db
      matchmaking(ws, obj)
    } else if (obj.type === "makeMove") {
      makemove(obj)
    } else if (obj.type === "getLeaderboard"){
      getLeaderboard(ws)
    } else if (obj.type === "finishGame") {
      finishGame(ws, obj)
    }
  });
  // ws.send('receiveMove');
});

function getLeaderboard(ws) {
  Player.find((err, players) => {
    if (err) {
      const msg = {type: "receiveLeaderboard", error: err, players: null}
      ws.send(JSON.stringify(msg))
    } else {
      players.sort((a,b) => (b.elo - a.elo))
      const msg = {type: "receiveLeaderboard", error: null, players: players}
      ws.send(JSON.stringify(msg))
    }
  })
}

function matchmaking(ws, obj) {
  if (matchMakingQueues[obj.gameMode] === undefined) {
    matchMakingQueues[obj.gameMode] = []
  }
  const username = obj.username
  const queue = matchMakingQueues[obj.gameMode]
  queue.push([ws, username])
  if (queue.length == 2) {
    const [p1ws, p1username] = queue[0]
    const [p2ws, p2username] = queue[1]
    const seed = Math.random()
    p1ws.send(JSON.stringify({type:"startGame", player:1, opponentId: p2ws.id, opponentUsername: p2username, seed}))
    p2ws.send(JSON.stringify({type:"startGame", player:2, opponentId: p1ws.id, opponentUsername: p1username, seed}))
    matches[p1ws] = [p1username, p2username]
    matches[p2ws] = [p2username, p1username]
    queue.length = 0
  }
}

function makemove(obj) {
  const msg = {type: "receiveMove", move: obj.move, opponentId: obj.opponentId}
  const wsToSend = players[obj.opponentId]
  wsToSend.send(JSON.stringify(msg));
}

function finishGame(ws, obj) {
  const [myUsername, opponentUsername] = matches[ws]
  Player.find((err, players) => {
    myPlayer = players.find(p => p.username == myUsername)
    opponentPlayer = players.find(p => p.username == opponentUsername)
    myNewElo = getNewRating(myPlayer.elo, opponentPlayer.elo, obj.gameResult)
    opponentNewElo = getNewRating(opponentPlayer.elo, myPlayer.elo, 1 - obj.gameResult)
    Player.findByIdAndUpdate({ _id: myPlayer._id}, 
      {elo: myNewElo},
      (err, res) => {
        if (err) {
          console.error(err)
        } else {
          console.log(res);
        }
      }
    )
    Player.findByIdAndUpdate({ _id: opponentPlayer._id}, 
      {elo: opponentNewElo},
      (err, res) => {
        if (err) {
          console.error(err)
        } else {
          console.log(res);
        }
      }
    )
  })
}

server.listen(8080);

function getNewRating(myRating, opponentRating, gameResult) {
  return myRating + getRatingDelta(myRating, opponentRating, gameResult)
}

function getRatingDelta(myRating, opponentRating, gameResult) {
  if ([0, 0.5, 1].indexOf(gameResult) === -1) {
    return null;
  }
  var myChanceToWin = 1 / ( 1 + Math.pow(10, (opponentRating - myRating) / 400));

  return Math.round(32 * (gameResult - myChanceToWin));
}