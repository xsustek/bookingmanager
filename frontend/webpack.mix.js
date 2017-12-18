let mix = require('laravel-mix');

mix.react('react-src/js/index.js', 'src/main/webapp/js');

mix.setPublicPath("src");