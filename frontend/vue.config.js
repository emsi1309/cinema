const { defineConfig } = require('@vue/cli-service');

module.exports = defineConfig({
  devServer: {
    port: 8081,
    proxy: {
      '/upload': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }  },
  transpileDependencies: ['vue-toastification']
});
