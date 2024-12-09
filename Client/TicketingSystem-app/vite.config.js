// vite.config.js
import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import { NodeGlobalsPolyfillPlugin } from '@esbuild-plugins/node-globals-polyfill';

export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {
      global: 'global',
    },
  },
  optimizeDeps: {
    esbuildOptions: {
      define: {
        global: 'globalThis', // Fixes 'global' is not defined
      },
      plugins: [
        NodeGlobalsPolyfillPlugin({
          process: true,
          buffer: true,
        }),
      ],
    },
  },
  server: {
    port: 5173, // Ensure that Vite always uses port 5173
    proxy: {
      '/ws': {
        target: 'http://localhost:8080', // Backend server
        changeOrigin: true,
        ws: true,
      },
    },
  },
});
