import {Injectable} from '@angular/core';
import {Callback, CognitoUtil} from './cognito.service';

@Injectable()
export class UserParametersService {

    constructor(public cognitoUtil: CognitoUtil) {
    }

    getParameters(callback: Callback) {
        const cognitoUser = this.cognitoUtil.getCurrentUser();

        if (cognitoUser != null) {
            // tslint:disable-next-line:only-arrow-functions
            cognitoUser.getSession(function (err, session) {
                if (err) {
                    console.log('UserParametersService: Couldn\'t retrieve the user');
                } else {
                    // tslint:disable-next-line:only-arrow-functions no-shadowed-variable
                    cognitoUser.getUserAttributes(function(err, result) {
                        if (err) {
                            console.log('UserParametersService: in getParameters: ' + err);
                        } else {
                            callback.callbackWithParam(result);
                        }
                    });
                }

            });
        } else {
            callback.callbackWithParam(null);
        }


    }
}
