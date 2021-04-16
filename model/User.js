var mogose = require('mongoose')
var userChema = new mogose.Schema({
    name: String,
    age: Number,
    address: String

})
module.exports=mogose.model('user',userChema)