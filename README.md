# Sample serverless fullstack Kotlin project

This is a template project to build a serverless project utilizing all of:
1. Kotlin multiplatform libraries to share code between backend and frontend.
1. AWS lambda functions to achieve high scalability.
1. Frontend having static pages and Angular single page application.
1. [Serverles Framework](https://serverless.com/) to automate deployment.

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
The last stage of deployment takes about 10-20 minutes for creating CloudFront 
distribution. Be ready to wait for that.

After deployment is done you can go to the web address: `https://v001.your-domain.com`.
The prefix v001 is your project stage.

To attach some of your web-site versions you need to go to your CloudFront settings and do following.
1. Be sure your certificate includes both `v001.your-domain.com` and `your-domain.com`. Check that in your 
[Certificate Manager](https://console.aws.amazon.com/acm/home?region=us-east-1#/).
1. In the CloudFront distribution settings add your base domain to Alternate Domain Names (CNAMEs).
1. In [Route 53 Hosted Zones](https://console.aws.amazon.com/route53/home?region=us-east-1#hosted-zones:)  
choose your-domain.com `A` settings and configure it as ALIAS to `v001.your-domain.com`.

Too many steps but this is AWS. 
