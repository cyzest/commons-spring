# Changelog
라이브러리의 모든 변경사항을 기록합니다.   
라이브러리는 1.0.5버전 이상부터 실사용이 가능합니다.   
변경사항의 작성 포멧은 [Keep a Changelog](https://keepachangelog.com/en/1.0.0/)를 따릅니다.

## [1.0.6] - 2018-08-18
### Added
- no added.

### Changed
- EnumTypeable 인터페이스 명칭 수정 (EnumTypeable -> EnumCode)
- ExceptionTypeable 인터페이스 명칭 수정 (ExceptionTypeable -> ExceptionType)
- EnumCode, ExceptionType 명칭 수정에 따른 코드 리펙토링
- EnumTypeConverter → EnumCodeConverter 명칭 변경
- EnumTypeHandler → EnumCodeHandler 명칭 변경
- EnumTypeAnnotationProcessor → EnumCodeAnnotationProcessor 명칭 변경
- JpaEnumTypeConverter → JpaEnumCodeConverter 명칭 변경
- MybatisEnumTypeHandler → MybatisEnumCodeHandler 명칭 변경
- EnumTypePropertyEditor → EnumCodePropertyEditor 명칭 변경


### Removed
- no removed.

## [1.0.5] - 2018-08-15
### Added
- EnumTypeable 인터페이스 추가
- ExceptionTypeable 인터페이스 추가
- BooleanFlag Enum 추가
- BasedException 익셉션 추가
- JPA EnumType Converter 추상클래스 추가
- JPA EnumType Converter APT용 어노테이션 추가
- MyBatis EnumType Handler 추상클래스 추가
- MyBatis EnumType Handler APT용 어노테이션 추가
- EnumType Annotation Processor 추가
- BooleanFlag JPA EnumType Converter 추가
- BooleanFlag MyBatis EnumType Converter 추가
- MyBatis DAO 추상클래스 추가
- Batch Mode SqlSessionTemplate 추가
- FileType Bean Validator 추가
- SLF4J MDC TaskDecorator 추가
- API ExceptionHandler 추상클래스 추가
- Custom MultipartResolver 추가
- Custom ResponseErrorHandler 추가
- HTTP Utils 추가

### Changed
- no changed.

### Removed
- no removed.