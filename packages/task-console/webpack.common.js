const path = require('path');
const webpack = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const TsconfigPathsPlugin = require('tsconfig-paths-webpack-plugin');
const webpack = require('webpack');
const BG_IMAGES_DIRNAME = 'bgimages';

module.exports = {
  entry: {
    app: path.resolve(__dirname, 'src', 'index.tsx')
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: path.resolve(__dirname, 'src', 'index.html'),
      favicon: 'src/favicon.ico'
    }),
    new webpack.EnvironmentPlugin({
      KOGITO_AUTH_ENABLED: false,
      KOGITO_KEYCLOAK_REALM: "kogito",
      KOGITO_KEYCLOAK_URL: "http://localhost:8280",
      KOGITO_KEYCLOAK_CLIENT_ID: "kogito-management-console",
      KOGITO_DATAINDEX_HTTP_URL: "http://localhost:4000"
    })
  ],
  module: {
    rules: [
      {
        test: /\.(tsx|ts)?$/,
        include: path.resolve(__dirname, 'src'),
        use: [
          {
            loader: 'ts-loader',
            options: {
              configFile: path.resolve('./tsconfig.json'),
              allowTsInNodeModules: true
            }
          }
        ]
      },
      {
        test: /\.(svg|ttf|eot|woff|woff2)$/,
        include: [
          path.resolve('../../node_modules/patternfly/dist/fonts'),
          path.resolve(
            '../../node_modules/@patternfly/react-core/dist/styles/assets/fonts'
          ),
          path.resolve(
            '../../node_modules/@patternfly/react-core/dist/styles/assets/pficon'
          ),
          path.resolve(
            '../../node_modules/@patternfly/patternfly/assets/fonts'
          ),
          path.resolve(
            '../../node_modules/@patternfly/patternfly/assets/pficon'
          ),
<<<<<<< HEAD
          path.resolve('./src/static')
=======
          path.resolve('./src/static'),
          path.resolve('../../node_modules/@kogito-apps/common/src/static')
>>>>>>> master
        ],
        use: {
          loader: 'file-loader',
          options: {
            // Limit at 50k. larger files emited into separate files
            limit: 5000,
            outputPath: 'fonts',
            name: '[name].[ext]'
          }
        }
      },
      {
        test: /\.svg$/,
        include: input => input.indexOf('background-filter.svg') > 1,
        use: [
          {
            loader: 'url-loader',
            options: {
              limit: 5000,
              outputPath: 'svgs',
              name: '[name].[ext]'
            }
          }
        ]
      },
      {
        test: /\.svg$/,
        include: input => input.indexOf(BG_IMAGES_DIRNAME) > -1,
        use: {
          loader: 'svg-url-loader',
          options: {}
        }
      },
      {
<<<<<<< HEAD
        test: /\.(jpg|jpeg|png|gif)$/i,
=======
>>>>>>> master
        include: [
          path.resolve(__dirname, 'src'),
          path.resolve('../../node_modules/patternfly'),
          path.resolve(
            '../../node_modules/@patternfly/patternfly/assets/images'
          ),
          path.resolve(
            '../../node_modules/@patternfly/react-styles/css/assets/images'
          ),
          path.resolve(
            '../../node_modules/@patternfly/react-core/dist/styles/assets/images'
          ),
          path.resolve(
            '../../node_modules/@patternfly/react-core/node_modules/@patternfly/react-styles/css/assets/images'
          ),
          path.resolve(
            '../../node_modules/@patternfly/react-table/node_modules/@patternfly/react-styles/css/assets/images'
          ),
          path.resolve(
            __dirname,
            '../../node_modules/@kogito-apps/common/src/static'
          )
        ],
        use: [
          {
            loader: 'url-loader',
            options: {
              limit: 5000,
              outputPath: 'images',
              name: '[name].[ext]'
            }
          }
        ]
      }
    ]
  },
  output: {
    filename: '[name].bundle.js',
    path: path.resolve(__dirname, 'dist'),
    publicPath: '/'
  },
<<<<<<< HEAD
=======
  plugins: [
    new HtmlWebpackPlugin({
      favicon: 'src/favicon.ico',
      template: path.resolve(__dirname, 'src', 'index.html')
    }),
    new webpack.EnvironmentPlugin({
      KOGITO_AUTH_ENABLED: false,
      KOGITO_KEYCLOAK_REALM: 'kogito',
      KOGITO_KEYCLOAK_URL: 'http://localhost:8280',
      KOGITO_KEYCLOAK_CLIENT_ID: 'kogito-task-console',
      KOGITO_DATAINDEX_HTTP_URL: 'http://localhost:4000/graphql',
      KOGITO_APP_VERSION: 'DEV',
      KOGITO_APP_NAME: 'Task Console'
    })
  ],
>>>>>>> master
  resolve: {
    extensions: ['.ts', '.tsx', '.js'],
    modules: [
      path.resolve('../../node_modules'),
      path.resolve('./node_modules'),
      path.resolve('./src')
    ],
    plugins: [
      new TsconfigPathsPlugin({
        configFile: path.resolve(__dirname, './tsconfig.json')
      })
    ],
    symlinks: false,
    cacheWithContext: false
  }
};
