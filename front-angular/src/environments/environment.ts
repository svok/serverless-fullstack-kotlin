// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
    production: false,
    ddbTableName: 'users_table',
    sts_endpoint: 'sts_endpoint',
    identityPoolId: 'sdf',
    dynamodb_endpoint: 'dynamodb_endpoint',
    userPoolId: 'us-east-1_DC0YQH2jn',
    clientId: '49tktmcpqgs8ge7dmdo9cq44kp',
    cognito_idp_endpoint: 'cognito_idp_endpoint',
    cognito_identity_endpoint: 'cognito_identity_endpoint',
    region: 'us-east-1',
    bucketRegion: 'bubketRegion',
    rekognitionBucket: 'rekognitionBucket',
    s3_endpoint: 's3_endpoint',
    albumName: 'albumName'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
