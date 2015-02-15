var gulp = require('gulp'),
    concat = require('gulp-concat'),
    rename = require('gulp-rename'),
    less = require('gulp-less');

var paths = {
    styles : 'public/styles/less/main.less'
};

gulp.task('deps', function () {
    gulp.src([
        'bower_components/angular/angular.js',
        'bower_components/angular-aria/angular-aria.js',
        'bower_components/angular-animate/angular-animate.js',
        'bower_components/angular-material/angular-material.js',
        'bower_components/angular-messages/angular-messages.js',
        'bower_components/highlightjs/highlight.pack.js',
        'bower_components/angular-highlightjs/angular-highlightjs.js',

        'bower_components/angular-ui-router/release/angular-ui-router.js',
        'bower_components/angular-translate/angular-translate.js'
    ])
        .pipe(concat('vendor.js'))
        .pipe(gulp.dest('public/javascripts/'))
});

gulp.task('deps_test', function () {
    gulp.src([])
        .pipe(concat('lvr-vendor-test.js'))
        .pipe(gulp.dest('public/js/'))
});

gulp.task('less', function () {
    gulp.src(paths.styles)
        .pipe(less())
        .pipe(rename('main.css'))
        .pipe(gulp.dest('public/styles/'))
});

gulp.task('watch', function () {
    gulp.watch(paths.styles, ['less'])
});