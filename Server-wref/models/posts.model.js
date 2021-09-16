const mongoose = require('mongoose')

const postsShema = new mongoose.Schema({
    idUser:{
        type: mongoose.Schema.Types.ObjectId,
        require: true,
        ref: 'User'
    },
    idLocation:{
        type: mongoose.Schema.Types.ObjectId,
        require: true,
        ref: 'Location'
    },
    content:{
        type: String,
        require: true
    },
    media:[String],
    Like:[{
        iduserLike:{
            type:mongoose.Schema.Types.ObjectId, 
            ref: 'User' 
        }
    }],
    Comment: 
    {
        type:Number,
        required: true
    },
    create_at:{
        type: Number,
        require: true
    },
    update_at:{
        type: Number,
        require: true
    }
})

const Posts = mongoose.model('Posts', postsShema, 'posts')

module.exports = Posts