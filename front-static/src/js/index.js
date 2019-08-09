import $ from 'jquery';
window.jQuery = $;
window.$ = $;

import popper from 'popper.js';
import bootstrap from 'bootstrap';
import * as config from './config';

window.AmazonCognitoIdentity = require('amazon-cognito-identity-js');
