const mongoose = require('mongoose')

const PlayerSchema = mongoose.Schema({
  username: {
    type: String,
    required: true,
    unique: true
  },
  elo: {
    type: Number,
    default: 1500
  }
});


module.exports = mongoose.model('Player', PlayerSchema)