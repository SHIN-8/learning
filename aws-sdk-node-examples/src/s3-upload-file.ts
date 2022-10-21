import * as AWS from 'aws-sdk'
import * as stream from 'fs'
import { Readable } from 'stream'
import * as path from 'path'

/**
 * アップロードリクエストパラメータ
 */
export type UploadParam = {
    key: string
    body: string | Buffer | Uint8Array | Blob | Readable
    bucket: string
}

/**
 * ダウンロードリクエストパラメータ
 */
export type DownloadParam = {
    key: string
    bucket: string
}

/**
 * リストアップパラメータ
 */
export type ListParam = {
    bucket: string
}

/**
 * 存在チェックリクエストパラメータ
 */
export type ExistParam = {
    key: string
    bucket: string
}

/**
 * コピーリクエストパラメータ
 */
export type CopyParam = {
    key: string
    destination: string
    bucket: string
}

/**
 * 削除リクエストパラメータ
 */
export type DeleteParam = {
    key: string
    bucket: string
}

/**
 * ストレージインタフェース
 */
export interface IStore {
    /**
     * ディレクトリの内容をリストアップします
     * @param param 
     */
    list(param: ListParam): Promise<void>;

    /**
     * 指定したファイルをダウンロードします
     * @param param 
     */
    download(param: DownloadParam): Promise<void>;

    /**
     * 指定したファイルをアップロードします
     * @param param 
     */
    upload(param: UploadParam): Promise<void>

    /**
     * 指定したファイルが存在するかチェックします
     * @param param
     */
    exist(param: UploadParam): Promise<void>

    /**
     * 指定したファイルをコピーします
     * @param param 
     */
    copy(param: CopyParam): Promise<void>

    /**
     * 指定したファイルを削除します
     * @param param 
     */
    delete(param: DeleteParam): Promise<void>
}

/**
 * ファクトリインタフェース
 */
export interface IFactory<T, U> {
    create(t: T): U
}

/**
 * ストレージタイプ列挙子
 */
export enum StorageType {
    Nyx,
    S3
}

/**
 * ストレージファクトリー
 */
export class StorageFactory implements IFactory<StorageType, IStore>
{
    create(type: StorageType): IStore {
        switch (type) {
            case StorageType.Nyx:
                return new NyxStorage()
            case StorageType.S3:
                return new S3Storage();
            default:
                throw new NotImplementationException();
        }
    }
}

export class S3Storage implements IStore {

    private _client?: AWS.S3;

    /**
     * コンストラクタ
     */
    constructor() {
        this._client = new AWS.S3({ apiVersion: '2006-03-01', useAccelerateEndpoint: true, });
    }
    async list(param: ListParam): Promise<void> {
        const request: AWS.S3.ListObjectsRequest = {
            Bucket: param.bucket
        }
        this._client?.listObjects(request, function (err, data) {
            if (err) {
                console.log("Error", err);
            } if (data) {
                console.log(data.Contents)
            }
        })
    }

    async download(param: DownloadParam): Promise<void> {
        const request: AWS.S3.GetObjectRequest = {
            Key: param.key,
            Bucket: param.bucket
        }
        const response = await this._client?.getObject(request).promise()

        console.log(response?.$response)
    }

    async upload(param: UploadParam): Promise<void> {
        const uploadParams: AWS.S3.PutObjectRequest = {
            Body: param.body,
            Key: param.key,
            Bucket: param.bucket
        }

        const response = await this._client?.upload(uploadParams, function (err, data) {
            if (err) {
                console.log("Error", err);
            } if (data) {
                console.log("Upload Success", data.Location);
            }
        }).promise();

        console.log(response?.Key)
    }

    async uploadMultipart(fileName: string, bucket: string, bufferSize: number) {
        //マルチパートアップロードリクエストを生成
        const multiPartRequest = await this._client?.createMultipartUpload({
            Key: path.basename(fileName),
            Bucket: "dev-kingprinters-data"
        }).promise();

        //マルチパートアップロードで使用するUploadIdを取得
        const uploadId = multiPartRequest?.UploadId!

        //Promsie.allするためのpromiseの配列
        let promises: Array<Promise<any>> = []

        const reader = stream.createReadStream(fileName, { highWaterMark: 1024 * 1024 * 5, encoding: "binary" })
            //createReadで5MB単位で読み込んでパートアップロードする
            .on('data', (chunk) => {
                console.log(`${chunk.length} byte読み込みました`)
                let part: Promise<any> | undefined = this._client?.uploadPart({
                    Key: path.basename(fileName),
                    PartNumber: promises.length + 1,
                    Body: chunk,
                    UploadId: uploadId,
                    Bucket: "dev-kingprinters-data"
                }).promise();

                console.log(`${promises.length} リクエスト`)

                if (!part) {
                    throw new Error("undefined!!")
                }
                const used: any = process.memoryUsage()
                let messages: Array<string> = []
                for (let key in used) {
                    messages.push(`${key}: ${Math.round(used[key] / 1024 / 1024 * 100) / 100} MB`)
                }
                console.log(new Date(), messages.join(', '))
                promises.push(part)
            })
            //データをすべて読み終えたら、Promiseallを実施して、completeさせる
            .on('close', async () => {
                const uploadResult: any[] = await Promise.all(promises)
                const partInfo = uploadResult.map((value, index) => {
                    return {
                        ETag: value.ETag,
                        PartNumber: index + 1
                    }
                })

                const completeResult = await this._client?.completeMultipartUpload({
                    Key: path.basename(fileName),
                    MultipartUpload: { Parts: partInfo },
                    UploadId: uploadId,
                    Bucket: "dev-kingprinters-data"
                }).promise().catch(err => {
                    console.log(err)
                })

                console.log("done")
                reader.close()
            })
            //読み込みエラー時の処理
            .on('error', (err: Error) => {
                console.log('File Error', err);
            })
    }

    async exist(param: ExistParam): Promise<void> {
        const downloadRequest: AWS.S3.GetObjectRequest = {
            Key: param.key,
            Bucket: param.bucket
        }
        this._client?.getObject(downloadRequest, (err, data) => {
            if (err) {
                console.log("Error", err);
            } if (data) {
                console.log("Upload Success", data);
            }
        })
    }

    async copy(param: CopyParam): Promise<void> {
        const request: AWS.S3.CopyObjectRequest = {
            Key: param.destination,
            CopySource: param.key,
            Bucket: param.bucket
        }
        const response = await this._client?.copyObject(request).promise()
        console.log(response?.$response)
    }

    async delete(param: DeleteParam): Promise<void> {
        const request: AWS.S3.DeleteObjectRequest = {
            Key: param.key,
            Bucket: param.bucket
        }
        const response = await this._client?.deleteObject(request).promise()

        console.log(response?.$response)
    }
}

export class NyxStorage implements IStore {
    async list(param: ListParam): Promise<void> {
        throw new NotImplementationException()
    }
    async download(param: DownloadParam): Promise<void> {
        throw new NotImplementationException()
    }

    async upload(param: UploadParam): Promise<void> {
        throw new NotImplementationException()
    }

    async exist(param: ExistParam): Promise<void> {
        throw new NotImplementationException()
    }

    async copy(param: CopyParam): Promise<void> {
        throw new NotImplementationException()
    }

    async delete(param: DeleteParam): Promise<void> {
        throw new NotImplementationException()
    }
}

export class NotImplementationException extends Error {

}

function sleep(ms: number) {
    return new Promise((resolve) => {
        setTimeout(resolve, ms);
    });
}

async function main() {
    const factory = new StorageFactory();
    const storage: S3Storage = factory.create(StorageType.S3) as S3Storage

    const bufferSize = 1024 * 1024 * 5 // 5MBごと
    //サンプル入力
    const fileName1 = '/Users/kpu0471/Documents/repositories/experiments/s3/src/dummy.txt';
    const fileName2 = '/Users/kpu0471/Documents/repositories/experiments/s3/src/dummy2.txt';
    const bucketName = 'dev-kingprinters-data'

    //バケットの内容一覧取得
    console.log("バケットの内容一覧取得")
    await storage.list({ bucket: bucketName })
    await sleep(1000)

    //アップロード
    console.log("普通のアップロード")
    const body = stream.readFileSync(fileName1)
    await storage.upload({ key: path.basename(fileName1), bucket: bucketName, body: body }).catch(err => {
        throw new Error
    })
    await sleep(1000)


    //マルチパートアップロード
    console.log("マルチパートアップロード")
    await storage.uploadMultipart(fileName2, bucketName, bufferSize)
    await sleep(1000)


    //ダウンロード
    console.log("ダウンロード")
    await storage.download({ key: path.basename(fileName1), bucket: bucketName })
    await sleep(1000)

    //コピー
    console.log("コピー")
    await storage.copy({ key: path.basename(fileName1), destination: `${path.basename(fileName1)}のコピー`, bucket: bucketName })
    await sleep(1000)

    //
    console.log("削除")
    await storage.delete({ key: path.basename(fileName1), bucket: bucketName })
    await sleep(1000)
}

main().catch(err => {
    console.log(err)
})
