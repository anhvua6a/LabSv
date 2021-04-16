var express = require('express');
var router = express.Router();
var User = require('../model/User')

/* GET home page. */
router.get('/',async function(req, res, next) {
  await User.find({}).then((users)=>{
    console.log(users)
     res.render('index', { users });
  })
 
});

//add
router.post('/add',async function(req, res, next) {
  let {name,age,address} = req.body
  let user = new User({name,age,address})
   user.save().then(res.redirect('/')).catch(res.send('lỗi'))
 
});
// delete
router.get('/delete/:id',async (req,res)=>{
  let id=req.params.id
  await User.findOneAndDelete({_id:id}).then(
    res.redirect('/')
  ).catch(res.send('lỗi'))
} )

//update
router.post('/update/:id',async (req,res)=>{
  let id=req.params.id
  await User.findOneAndUpdate({_id:id},{$set:{
    name:req.body.name,
    age:req.body.age,
    address:req.body.address
  }},{new:true}).then(
    res.redirect('/')
  )
})

module.exports = router;
