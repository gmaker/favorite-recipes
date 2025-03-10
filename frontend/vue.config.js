const { defineConfig } = require('@vue/cli-service');

module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8081',
        changeOrigin: false,
      },
      '/uploads': {
        target: 'http://localhost:8081',
        changeOrigin: false,
      },
    },
  },
});
