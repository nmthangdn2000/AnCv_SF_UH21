const mongoose = require('mongoose')

const locationShema = new mongoose.Schema({
    longitude:{
        type: Number,
        require: true
    },
    latiude:{
        type: Number,
        require: true
    },
    name:{
        type: String,
        require: true
    }
})

const Location = mongoose.model('Location', locationShema, 'locations')

module.exports = Location