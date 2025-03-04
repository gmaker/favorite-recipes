const { defineConfig } = require('@vue/cli-service');

module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      '/uploads': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        // если нужно, можно настроить pathRewrite
        // pathRewrite: { '^/uploads': '/uploads' },
      },
      '/api': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        // если нужно, можно настроить pathRewrite
        // pathRewrite: { '^/uploads': '/uploads' },
      },
    },
  },
});
