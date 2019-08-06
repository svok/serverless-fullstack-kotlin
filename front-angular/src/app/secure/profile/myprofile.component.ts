import {Component} from '@angular/core';
import {UserLoginService} from '../../service/user-login.service';
import {Callback, CognitoUtil, LoggedInCallback} from '../../service/cognito.service';
import {UserParametersService} from '../../service/user-parameters.service';
import {Router} from '@angular/router';


@Component({
    selector: 'app-awscognito-angular2',
    templateUrl: './myprofile.html'
})
export class MyProfileComponent implements LoggedInCallback {

    public parameters: Array<Parameters> = [];
    public cognitoId = '';

    constructor(
        public router: Router,
        public userService: UserLoginService,
        public userParams: UserParametersService,
        public cognitoUtil: CognitoUtil
    ) {
        this.userService.isAuthenticated(this);
        console.log('In MyProfileComponent');
    }

    isLoggedIn(message: string, isLoggedIn: boolean) {
        if (!isLoggedIn) {
            this.router.navigate(['/home/login']);
        } else {
            this.userParams.getParameters(new GetParametersCallback(this, this.cognitoUtil));
        }
    }
}

export class Parameters {
    name: string;
    value: string;
}

export class GetParametersCallback implements Callback {

    constructor(public me: MyProfileComponent, public cognitoUtil: CognitoUtil) {

    }

    callback() {

    }

    callbackWithParam(result: any) {

        for (const res of result) {
            const parameter = new Parameters();
            parameter.name = res.getName();
            parameter.value = res.getValue();
            this.me.parameters.push(parameter);
        }
        const param = new Parameters();
        param.name = 'cognito ID';
        param.value = this.cognitoUtil.getCognitoIdentity();
        this.me.parameters.push(param);
    }
}
