const AWS = require('aws-sdk')

// Set the region 
AWS.config.update({ region: 'ap-northeast-1' });

// Create S3 service object
const s3 = new AWS.S3({ apiVersion: '2006-03-01' });

// Call S3 to list the buckets
s3.listBuckets(function (err: any, data: any) {
    if (err) {
        console.log("Error", err);
    } else {
        console.log("Success", data.Buckets);
    }
});