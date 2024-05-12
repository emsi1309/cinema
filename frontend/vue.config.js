const { defineConfig } = require('@vue/cli-service');

module.exports = defineConfig({
  devServer: {
    port: 8081 // Thay đổi cổng thành 8081
  },
  transpileDependencies: ['vue-toastification']
});
