module.exports = {
  root: true,
  env: {
    browser: true,
    node: true,
    es6: true,
  },
  parser: '@typescript-eslint/parser', // Specifies the ESLint parser
  parserOptions: {
    ecmaVersion: 'latest', // Allows for the parsing of modern ECMAScript features
    sourceType: 'module', // Allows for the use of imports
    ecmaFeatures: {
      jsx: true, // Allows for the parsing of JSX
    },
  },
  settings: {
    react: {
      version: 'detect', // Tells eslint-plugin-react to automatically detect the version of React to use
    },
    'import/resolver': {
      typescript: {},
    },
  },
  // React 소스 일 경우
  extends: [
    'react-app',
    'plugin:react/recommended', // Uses the recommended rules from @eslint-plugin-react
    // 'plugin:@typescript-eslint/recommended', // Uses the recommended rules from the @typescript-eslint/eslint-plugin
    // 'plugin:prettier/recommended', // Enables eslint-plugin-prettier and eslint-config-prettier. This will display prettier errors as ESLint errors. Make sure this is always the last configuration in the extends array.
  ],
  plugins: ['@typescript-eslint', 'react', 'react-hooks'],

  // Server 소스 일 경우
  // extends: [
  //   'plugin:@typescript-eslint/recommended', // Uses the recommended rules from the @typescript-eslint/eslint-plugin
  //   'plugin:prettier/recommended', // Enables eslint-plugin-prettier and eslint-config-prettier. This will display prettier errors as ESLint errors. Make sure this is always the last configuration in the extends array.
  // ],
  // plugins: ['@typescript-eslint',],
  rules: {
    'no-console': process.env.NODE_ENV === 'prod' ? 'warn' : 'off',
    'no-debugger': process.env.NODE_ENV === 'prod' ? 'warn' : 'off',
    'import/extensions': ['off'],
    '@typescript-eslint/explicit-function-return-type': 'off',
  },
};
