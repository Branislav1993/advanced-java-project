var webpack = require('webpack');

module.exports = {
    context: __dirname,
    entry: ["./index.js"],
    output: {
        path: "./bundle",
        filename: "bundle.js"
    },
    module: {
        loaders: [
            { test: /\.json$/, loader: "json-loader" }
        ]
    },
    plugins: [
        new webpack.ProvidePlugin({
            $: "jquery",
            jQuery: "jquery",
            "window.jQuery": "jquery"
        })
    ]
};