import {Component, OnInit} from '@angular/core';

declare let AWS: any;
declare let AWSCognito: any;

@Component({
    selector: 'app-awscognito-angular2',
    template: '<p>Hello and welcome!"</p>'
})
export class AboutComponent {

}

@Component({
    selector: 'app-awscognito-angular2',
    templateUrl: './landinghome.html'
})
export class HomeLandingComponent {
    constructor() {
        console.log("HomeLandingComponent constructor");
    }
}

@Component({
    selector: 'app-awscognito-angular2',
    templateUrl: './home.html'
})
export class HomeComponent implements OnInit {

    constructor() {
        console.log("HomeComponent constructor");
    }

    ngOnInit() {

    }
}


