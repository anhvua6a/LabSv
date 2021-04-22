var express = require('express');
var router = express.Router();

var userModel = require('../model/User')

/* GET users listing. */
router.post('/login', async function (req, res, next) {
  let username = req.body.username
  let password = req.body.password

  await userModel.findOne({
    username: username
  }).then((users) => {
    if (password != users.password) {
      res.json({
        success: false,
        message: 'Mật khẩu không chính xác '
      })
      return
    }
    res.json({
      success: true,
      message: 'Đăng nhập thành công '
    })
  }).catch((error) => {
    res.json({
      success: false,message: error
    })
  })

});
//Đăng kí
router.post('/register', async function (req, res, next) {
  let { name, age, address, username, password } = req.body
  let user = new userModel({ name, age, address, username, password })
  user.save().then(() => {
    res.json({
      success: true,
      message: 'Đăng kí thành công'
    })
  }).catch((err) => {
    res.json({
      success: false,
      message: err
    })
  })

});

//Lấy danh sách users
router.get('/users', async function (req, res, next) {
  await userModel.find({}).then((users) => {
    res.json({
      success: true,
      message: 'OK',
      users
    })
  }).catch((error) => {
    res.json({
      success: false,
      message: error
    })
  })
});

//Xoá user
router.get('/user/delete/:id', async function (req, res, next) {
  let id = req.params.id
  await userModel.findOneAndDelete({ _id: id }).then(
    res.json({
      success: true,
      message: 'Xoá thành công'
    })
  ).catch((err) => {
    res.json({
      success: false,
      message: err
    })
  })
});
// update
router.post('/user/update/:id', async (req, res) => {
  let id = req.params.id
  await User.findOneAndUpdate({ _id: id }, {
    $set: {
      username: req.body.username,
      password: req.body.password,
      name: req.body.name,
      age: req.body.age,
      address: req.body.address
    }
  }, { new: true }).then(
    res.json({
      success: true,
      message: 'Update thành công'
    })
  ).catch((err) => {
    res.json({
      success: false,
      message: err
    })
  })
})
module.exports = router