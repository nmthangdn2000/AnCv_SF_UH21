const mongoose = require('mongoose')
mongoose.set('useNewUrlParser', true)
mongoose.set('useFindAndModify', false)
mongoose.set('useCreateIndex', true)
mongoose.set('useUnifiedTopology', true)
mongoose.connect(process.env.MONGO_URI)

const db = mongoose.connection
db.on('error', err => console.log("mongoose error: ", err))
.once('open', () => {
    console.log('DB Connect sucesses');
})
