module.exports = {
  devServer: {
    port: 8296
  },
  publicPath: process.env.NODE_ENV === 'production'
    ? './'
    : '/'
}
