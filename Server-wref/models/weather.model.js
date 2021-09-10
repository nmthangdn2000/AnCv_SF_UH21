const mongoose = require('mongoose')

const weatherShema = new mongoose.Schema({
    idLocation:{
        type: mongoose.Schema.Types.ObjectId,
        require: true,
        ref: 'Location'
    },
    infor:[]
})

const Weather = mongoose.model('Weather', weatherShema, 'weather')

module.exports = Weather