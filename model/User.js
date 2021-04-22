var mogose = require('mongoose')
var userChema = new mogose.Schema({
    username: String,
    password: String,
    name: String,
    age: Number,
    address: String

})
module.exports=mogose.model('user',userChema)