<?php
require __DIR__ . '/vendor/autoload.php';
use AlibabaCloud\Client\AlibabaCloud;
use AlibabaCloud\Client\Exception\ClientException;
use AlibabaCloud\Client\Exception\ServerException;

// Download：https://github.com/aliyun/openapi-sdk-php
// Usage：https://github.com/aliyun/openapi-sdk-php/blob/master/README.md


$IP = $_POST['ip'];
echo($IP);
try {
    AlibabaCloud::accessKeyClient('<assess key>', '<ass pwd>')
                        ->regionId('cn-hangzhou')
                        ->asDefaultClient();
                        
    $result = AlibabaCloud::rpc()
                          ->product('Alidns')
                          // ->scheme('https') // https | http
                          ->version('2015-01-09')
                          ->action('AddDomainRecord')
                          ->method('POST')
                          ->host('alidns.aliyuncs.com')
                          ->options([
                                        'query' => [
                                          'RegionId' => "cn-hangzhou",
                                          'DomainName' => "sidcloud.cn",
                                          'RR' => "域名前缀",
                                          'Type' => "A",
                                          'Value' => "$IP",
                                        ],
                                    ])
                          ->request();
    print_r($result->toArray());
} catch (ClientException $e) {
    echo $e->getErrorMessage() . PHP_EOL;
} catch (ServerException $e) {
    echo $e->getErrorMessage() . PHP_EOL;
}
?>