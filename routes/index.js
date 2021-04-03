var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  process.argv.forEach(function(arg, index) {
    console.log("argv[" + index + "] = " + arg);
    });
  res.render('index', { title: 'Express' });
});

module.exports = router;
