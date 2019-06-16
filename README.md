# Sample serverless fullstack Kotlin project

This is a template project to build a serverless project utilizing all of:
1. Kotlin multiplatform libraries to share code between backend and frontend.
1. AWS lambda functions to achieve high scalability.
1. Frontend having static pages and Angular single page application.
1. [Serverles Framework](https://serverlessp.com/) to automate deployment.

## Installation

Clone the sample project to your local directory.
```bash
git clone https://github.com/svok/serverless-fullstack-kotlin.git
cd serverless-fullstack-kotlin
```

Then you need to specify your settings in the `gradle-local.properties` file.
```properties
projectGroup=com.you-domain.your-group
projectDomain=your-domain.com
projectStage=v001
projectService=com-domain-your
projectVersion=0.1.2

domainCertificate=arn:aws:acm:us-east-1:000000000000:certificate/00000000-0000-0000-0000-000000000000
```

After all you can build and deploy your project.
```bash
./gradlew deploy
```
This will create an instance of API Gateway with name `v001.your-domain.com`. After that you need to
manually deploy it. For that go into `v001.your-domain.com` API Gateway. There choose `Resources`,
select '/' resource and in `Actions` button click `Deploy API`. After deployment is done you get an
address like `Invoke URL: https://6345dfgdsf.execute-api.us-east-1.amazonaws.com/v001`. Use in 
either `Custom domain names` or `CloudFront` configuration.   

## Requirements
1. Gradle 5.4
1. To use AWS CLI you need python 2.7 with aws-cli package installed. You can do this with the command:
```bash
pip3 install awscli --upgrade --user
```
