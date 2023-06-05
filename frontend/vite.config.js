import {fileURLToPath, URL} from 'node:url'

import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  define: {
    "global": {},
  },
  server: {
    host: "0.0.0.0",
    proxy: {
      '^/api/auth/login': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true
      },
      '^/error': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true
      },
      '^/api/auth/register': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true
      },
      '^/api/auth/logout': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true
      },
      '^/users': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true
      },
      '^/appusers': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true
      },
      '^/destinations': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true
      },
      '^/publicDestinations': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: true
      }
    },
    // port: 80
  }
})
