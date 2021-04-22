var mongoose = require('mongoose');
var url= 'mongodb+srv://admin:admin@cluster0.avwql.mongodb.net'

var db = mongoose.connect(url,{useNewUrlParser: true, useUnifiedTopology: true}).then(console.log('co lếch thành công')).catch(function(error){
    console.log(error)
})

module.exports=db

