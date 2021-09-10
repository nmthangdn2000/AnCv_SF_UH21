const mongoose = require('mongoose') 

const storiesSchema = new mongoose.Schema({
    idUser: {
        type: mongoose.Schema.Types.ObjectId,
        require: true,
        ref: 'User'
    },
    media:[String],
    like:[{
        iduserLike:{
            type:mongoose.Schema.Types.ObjectId, 
            ref: 'User' 
        }
    }],
    create_at:{
        type: Number,
        require: true
    }
})

const Stories = mongoose.model('Stories', storiesSchema, 'stories')

module.exports = Stories