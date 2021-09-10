// const Location = require('../models/location.model')
// const CronJob  = require('cron').CronJob
// const getData = require('./getDataWeather');

// const job = new CronJob(
//     '0 */3 * * *', function (){
//         const d = new Date()
//         console.log("cc me m ày   "+ d.getMinutes())
//         loadLocation()
//     }, null, true
// )
// job.start()

// async function loadLocation(){
//     const dataLocation = []
//     await Location.find()
//         .then(data => {
//             data.forEach(element => {
//                 dataLocation.push(element)
//             });
//         })
//         .catch(err=> console.log(err))
//     dataLocation.forEach(element => {
//         getData.restApi(element.latiude, element.longitude, element._id)
//     });

// }
