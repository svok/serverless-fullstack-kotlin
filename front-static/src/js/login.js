global.fetch = require('node-fetch');
import * as awsSdk from 'aws-sdk/dist/aws-sdk.min';
import * as amazonAuth from 'amazon-cognito-auth-js';
import * as config from './config';

window.AmazonCognitoIdentity = require('amazon-cognito-identity-js');
