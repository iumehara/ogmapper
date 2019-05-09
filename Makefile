.PHONY: build test generate_docs build_and_sign publish

build:
	./gradlew clean build -x Test

test:
	./gradlew test

generate_docs:
	./gradlew dokka

build_and_sign:
	./gradlew signMavenJavaPublication

publish:
	./gradlew publish
