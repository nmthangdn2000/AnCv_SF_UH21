const passport = require('passport')
const jwtStrategy = require('passport-jwt').Strategy
const LocalStrategy = require('passport-local').Strategy
const { ExtractJwt } = require('passport-jwt')
const { JWT_SECRET } = require('../config/appconfig')

const User = require('../models/user.model')

// passport jwt
passport.use(new jwtStrategy({
    jwtFromRequest: ExtractJwt.fromAuthHeaderAsBearerToken('Authorization'),
    secretOrKey: JWT_SECRET
}, async (payload, done) => {
    try {
        const user = await User.findById(payload.id)
        if(!user) return done(null, false)
        done(null, user)
    } catch (error) {
        done(error, false)
    }
}))

// passport local

passport.use(new LocalStrategy({
    usernameField: 'email'
}, async (email, password, done) => {
    try {
        console.log("a ", email, password);
        const user = await User.findOne({email})
        console.log("a ", user);
        if(!user) return done(null, false)
        const isCorrectPassword = await user.isValidPassword(password)
        if(!isCorrectPassword) return done(null, false)
        done(null, user)
    } catch (error) {
        done(error, false)
    }
}))