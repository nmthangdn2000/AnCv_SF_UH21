const mongoose = require('mongoose')
const bcrypt = require('bcryptjs')

const userSChema = new mongoose.Schema({
    userName:{
        type: String,
        required: true,
    },
    email:{
        type:String,
        required: true,
        unique: true
    },
    passWord:{
        type:String,
        required: true
    },
    avata: {
        type:String,
        require: true
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

userSChema.pre('save', async function(next){
    try {
        // Generatipn a salt...
        const salt = await bcrypt.genSalt(10)
        // Generation a password hash (salt + hash)
        const passwordHashed = await bcrypt.hash(this.passWord, salt)
        // Re-assign password hashed
        this.passWord = passwordHashed

        next()
    } catch (error) {
        next(error)
    }
})

userSChema.methods.isValidPassword = async function(newPassword){
    try {
        return await bcrypt.compare(newPassword, this.passWord)
    } catch (error) {
        throw new Error(error)
    }
}

const User = mongoose.model('User', userSChema, 'users')

module.exports = User