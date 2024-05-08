var path = require("path");
var gulp = require('gulp');
var md5 = require('gulp-md5-plus');

var requirejs = require('gulp-requirejs-simple');

var bPath = './resources/static/';
var paths = {
  css: [bPath + 'css/*.css', '!' + bPath + 'css/*.min.css'], //需要监控变化的css文件
  jss: [bPath + 'js/**/*.js', '!' + bPath + 'js/out/**/*.js'], //需要监控变化的js文件
  js: bPath + 'js/'
};

var minJsTask = {
  main: { //压缩bmap main.js
    baseUrl: paths.js,
    name: 'main.un',
    out: paths.js + 'main.js',
    optimize: 'uglify2',
    uglify: {},
    uglify2: {
      output: {
        screw_ie8: false,
        beautify: false
      },
      compress: {
        screw_ie8: false,
        sequences: false,
        global_defs: {
          DEBUG: false
        }
      },
      warnings: false,
      mangle: false
    },
    mainConfigFile: paths.js + 'config.require.js',
    watchFile: [paths.js + '*.js'] //需要监控变化的js文件
  }
}


//var jsTaskArr = [];
//for (task in minJsTask) {
//    gulp.task(task, requirejs(minJsTask[task]));
//    jsTaskArr.push(task);
//};
//gulp.task('minjs', jsTaskArr);

gulp.task('minjs', (cb) => {
  requirejs(minJsTask.main)(cb);
});

gulp.task('MD5', function (cb) {
  gulp.src(["./resources/static/js/*.js", "./resources/static/css/*.css"])
    .pipe(md5(5, ['./webapp/WEB-INF/views/common/include_js.ftl',
      './webapp/WEB-INF/views/common/include_resources.ftl',
      './webapp/WEB-INF/views/sys/login.ftl',
      './webapp/WEB-INF/views/sys/index.ftl'
    ], {
      mappingFile: 'manifest.json'
    })); // ,'./resources/static/js/config.require.js'
  // .pipe(gulp.dest("./resources/static/js"));
  cb();
});

gulp.task('default', gulp.series('minjs', 'MD5', function (cb) {
  console.log(process.cwd(), ' = finished = ^__^~~~~');
  cb();
}));