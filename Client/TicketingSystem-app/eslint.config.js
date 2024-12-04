// eslint.config.js
import { defineConfig } from 'eslint-define-config';
import globals from 'globals';

export default defineConfig({
  env: {
    browser: true,
    es2021: true,
    node: true,
  },
  extends: [
    'react-app',
    'eslint:recommended',
    'plugin:react/recommended',
    // Add other extensions as needed
  ],
  parserOptions: {
    ecmaFeatures: {
      jsx: true,
    },
    ecmaVersion: 12,
    sourceType: 'module',
  },
  plugins: [
    'react',
    // Add other plugins as needed
  ],
  rules: {
    // Define your custom rules here
  },
  globals: {
    ...globals.browser,
    ...globals.node,
    // Add any additional global variables if necessary
  },
});
