# Java 17の公式イメージを使用
FROM eclipse-temurin:17-jdk

# 作業ディレクトリを作成
WORKDIR /app

# ビルドされたJARファイルをコンテナにコピー
COPY target/ELBOOK.jar ELBOOK.jar

# ポートを公開
EXPOSE 8080

# アプリケーションを実行
ENTRYPOINT ["java", "-jar", "ELBOOK.jar"]