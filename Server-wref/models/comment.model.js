const mongoose = require('mongoose')

const commentShema = new mongoose.Schema({
    idUser:{
        type: mongoose.Schema.Types.ObjectId,
        require: true,
        ref: 'User'
    },
    idPosts:{
        type: mongoose.Schema.Types.ObjectId,
        require: true,
        ref: 'Posts'
    },
    idComment:{
        type: mongoose.Schema.Types.ObjectId,
        require: true,
    },
    content:{
        type: String,
        require: true
    },
    media:{
        type: String,
        require: true
    },
    Like:[{
        iduserLike:{
            type:mongoose.Schema.Types.ObjectId, 
            ref: 'User' 
        }
    }],
    create_at:{
        type: Number,
        require: true
    },
    update_at:{
        type: Number,
        require: true
    }

})

const Comment = mongoose.model('Comment', commentShema, 'comment')

module.exports = Comment