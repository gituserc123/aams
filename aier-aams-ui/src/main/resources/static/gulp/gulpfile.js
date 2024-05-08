var gulp = require('gulp');
var rename = require('gulp-rename');
var gulpFilter = require('gulp-filter');
var concat = require('gulp-concat');
var minifyCSS = require('gulp-minify-css');
var requirejs = require('gulp-requirejs-simple');
// var sass = require('gulp-ruby-sass');

var bPath = '../';
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
		uglify: {
		},
		uglify2: {
			output:{
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



var jsTaskArr = [];

for (task in minJsTask) {
    gulp.task(task, requirejs(minJsTask[task]));
    jsTaskArr.push(task);
};

gulp.task('default', requirejs(minJsTask.main));

gulp.task('mincss', function() {
    // var filter = gulpFilter(['*', '!*.min.css']);
    return gulp.src(paths.css)
        .pipe(minifyCSS({
            keepBreaks: true
        }))
        // .pipe(filter)
        .pipe(rename({
            suffix: '.min'
        }))
        .pipe(gulp.dest(bPath + 'css/'));
});

gulp.task('css', function() {
  return gulp.src([ bPath + 'css/socss.css',bPath + 'css/easy-plus.css', bPath + 'css/plugins.css',bPath + 'css/base.css',])
    .pipe(concat('all.css')).pipe(minifyCSS({
            keepBreaks: true
        }))
    .pipe(gulp.dest(bPath + 'css/'));
});

// gulp.task('sass', function() {
//     gulp.src(bPath + 'css/*.scss')
//         .pipe(sass({
//             style: 'compact',//nested, compact, compressed, expanded
//             lineNumbers : true,
//             //debugInfo : true,
//             quiet: true,
//         }))
//         .on('error', function(err) {
//             console.log(err.message);
//         })
//         .pipe(gulp.dest(bPath + 'css/'));
// });

// gulp.task('minsass', function() {
//     gulp.src(bPath + 'css/*.scss')
//         .pipe(sass({
//             style: 'compressed',//nested, compact, compressed, expanded
//             quiet: true,
//         }))
//         .on('error', function(err) {
//             console.log(err.message);
//         })
//         .pipe(gulp.dest(bPath + 'css/'));
// });

gulp.task('minjs', jsTaskArr);

gulp.task('watch', function() {
    // gulp.watch(paths.jss, ['minjs']);
    //gulp.watch(paths.css, ['mincss']);
    // gulp.watch(bPath + 'css/*.scss', ['sass']);

    gulp.watch(minJsTask.main.watchFile, ['main']);

    // for (task in minJsTask) {
    //     gulp.watch(task.watchFile,[task]);
    // };

});
