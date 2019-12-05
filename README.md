# PPMTool

Db ve phpmyadmin için
Linux işletim sisteminde docker kurulması gerekmektedir.
Dockera mysql:5.6 ve phpmyadmin/phpmyadmin container'ları kurulduktan sonra

docker run -p 3306:3306 -v {LOCALDE BİR KLASÖR}:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=ppmt -e MYSQL_USER=karaca -e MYSQL_PASSWORD=password -d mysql:5.6

docker run -d --link $(docker ps --format "{{.Names}}" --filter expose=3306-3306):db -p 8081:80 phpmyadmin/phpmyadmin

Bu iki komut çalıştırılmalıdır.
Sırası ile 
ilk komut ppmt isimli bir database oluşturup "karaca" isimli kullanıcı oluşturmakta ve şifresi "password" tür.
-v parametresi ile database'De yapılan değişiklikleri localde saklanabilecek bir klasör verilmelidir.
(windows işletim sisteminde burada sorun çıkmakta bu yüzden sağlıklı olabilmesi için linux tercih edilmeli)

ikinci komut ise phpmyadmin container'ını çalıştırmakta. önceki çalışan container'lardan porta göre filtre uygulayıp mysqlin
ID'sine ulaşıp mysql container'ına linklenip oluşturulan database'e bağlanmaya yarıyor
springboot ile çalışan uygulama defaultta 8080 portundan çalıştığından phpmyadmin 8081 portuna bağlandı.

özetle
localhost:8080 ' de web uygulamasına ulaşışmakta
localhost:8081 ' de phpmyadmine ulaşılmaktadır.
