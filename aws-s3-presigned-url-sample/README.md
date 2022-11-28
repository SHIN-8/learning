# Amazon S3 署名付き URL 使用サンプル

## Amazon S3 署名付き URL is 何？

[こちら](./docs/what_is_presigned_url.md)で簡単に解説。

## AWS SDK による署名付き URL の取得

以下はサーバーサイドで AWS SDK を使用して署名付き URL を発行する例。

発行した URL を ブラウザ

```php
    /**
     * Amazon S3 署名付き URL を発行する
     *
     * @return string
     */
    public function issuePresignedUrl(): string
    {
        $s3Client = new S3Client([
            'region' => 'ap-northeast-1',
            'version' => '2006-03-01',
            'credentials' => [
                'key' => '<S3_ACCESS_KEY>',
                'secret' => '<S3_SECRET_KEY>',
            ],
        ]);

        /**
         * 第一引数に S3 へのアクションを文字列で指定する。
         * WARNING: GetObject, PutObject, DeleteObject については検証済みだが，他のアクションは未検証。
         *
         * @var \Aws\CommandInterface
         * @see https://docs.aws.amazon.com/AmazonS3/latest/API/API_Operations_Amazon_Simple_Storage_Service.html
         */
        $cmd = $s3Client->getCommand('GetObject', [
            'Bucket' => '<SAMPLE_BUCKET>',
            'Key' => '<SAMPLE-KEY>,'
        ]);

        $request = $s3Client->createPresignedRequest($cmd, '+30 minutes');

        return (string)$request->getUri();
    }
```

### 参考

- [AWS SDK for PHP バージョン 3 での Amazon S3 の署名付き URL](https://docs.aws.amazon.com/ja_jp/sdk-for-php/v3/developer-guide/s3-presigned-url.html)

## 署名付き URL を curl で使用する

アクションに応じた HTTP メソッドでコールする。

[Amazon Simple Storage Service - API Reference](https://docs.aws.amazon.com/AmazonS3/latest/API/API_Operations_Amazon_Simple_Storage_Service.html)

### 具体例

S3 バケットにオブジェクトをアップロードする場合。

[PutObject - API Reference](https://docs.aws.amazon.com/AmazonS3/latest/API/API_PutObject.html)

この場合 HTTP メソッドは PUT なので，`curl` で `PUT` を指定する。

```sh
URL="<presigned-url>"

curl -X PUT --upload-file ./images/test-image.jpg $URL
```

:warning: このときアップロードされるファイルの S3 バケット上のパスとファイル名（オブジェクトのキー）は `--upload-file` オブションで指定したものではなく，署名付き URL を発行する際に `getCommand` メソッドで指定した `Key` となる。
