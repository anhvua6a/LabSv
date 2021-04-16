var createError = require('http-errors')
var express = require('express')
var path = require('path')
var cookieParser = require('cookie-parser')
var logger = require('morgan')

var multer  = require('multer')


var indexRouter = require('./routes/index')
var usersRouter = require('./routes/users')
var uploadRouter = require('./routes/upload')
var db = require('./mogoose')

var app = express()


var storage = multer.diskStorage({
  destination: function(req,file,cb){
    // đưa file vào thư mục chỉ định
    cb(null,'public/images')
    
  },
  filename:function(req,file,cb){
    cb(null,file.originalname)

  },
})
var upload = multer({storage,limits:{fileSize:2*1024*1024}, fileFilter: (req, file, cb)=> {
  if (file.mimetype != 'image/jpeg') {
    cb(new Error('K đúng định dạng ảnh'));
  } else {
    cb(null, true)
  }
}})
let uploads = multer({storage,limits:{files:5, fileSize:2*1024*1024}, fileFilter: (req, file, cb)=> {
  
 if (file.mimetype != 'image/jpeg') {
    cb(new Error('K đúng định dạng ảnh'));
  } else {
    cb(null, true)
  }
}})

// view engine setup
app.set('views', path.join(__dirname, 'views'))
app.set('view engine', 'hbs')

app.use(logger('dev'))
app.use(express.json())
app.use(express.urlencoded({ extended: false }))
app.use(cookieParser())
app.use(express.static(path.join(__dirname, 'public')))


app.use('/', indexRouter)
app.use('/users', usersRouter)

app.get('/upload',function(req,res){
  res.render('upload')
})
let uploadSingle = upload.single("avatar")
app.post('/upload',(req,res)=>{
  uploadSingle(req, res, (err)=> {
    if (err instanceof multer.MulterError) {
        res.send('file không đc quá 2mb')
        return
    } else if (err) {
      res.send(err.message)
      return
    }
    console.log(req.file)
    res.send('thành công')
  })
})

let uploadArr = uploads.array('avatar')
app.post('/uploadMulti',(req,res,next)=>{
  uploadArr(req, res, (err)=> {
     if (err instanceof multer.MulterError) {
       if (err.code == 'LIMIT_FILE_COUNT') {
          res.send('chỉ có thể up tối đa 5 ảnh!')
          return
       } else if (err.code == 'LIMIT_FILE_SIZE') {
        res.send('chỉ có thể up ảnh nhỏ hơn 2MB !')
        return
       }
  } else if (err) {
    res.send(err.message)
    return
  }
  console.log(req.files)
  res.send('up multi thành công')
  })
 
})

app.get('/adduser',function(req,res){
  res.render('add')
})
// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404))
})

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message
  res.locals.error = req.app.get('env') === 'development' ? err : {}

  // render the error page
  res.status(err.status || 500)
  res.render('error')
})
 

module.exports = app
