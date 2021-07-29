### Для запуска потребуется :
1. Скачать репозиторий `git clone https://github.com/jukkty/Diploma.git`
1. Запустить Docker и воспользоваться командой `docker-compose up -d`
### Чтобы открыть приложение в DB mysql :
 `java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar artifacts/aqa-shop.jar`
### Чтобы открыть приложение в DB postgres :
`java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -jar artifacts/aqa-shop.jar`
### Чтобы произошло соединение с нужной БД : 
В файле DatabaseInfo.java в строках 11 и 12 выбрать нужную БД и раскоментить ее
