## 📂 Schedule API 명세서

### 🔍 Base URL

```
/api/schedule
```

---

### ✅ API 목록 요약

| Method | Endpoint           | 설명              |
|--------|--------------------|-----------------|
| POST   | /api/schedule      | 일정 등록           |
| GET    | /api/schedule      | 일정 목록 조회 (페이징)  |
| GET    | /api/schedule/{id} | 일정 상세 조회        |
| PUT    | /api/schedule/{id} | 일정 수정           |
| DELETE | /api/schedule/{id} | 일정 삭제 (비밀번호 검증) |

---

### ✅ 1. 일정 등록

- **URL** : `POST /api/schedule`
- **요청 Body 필드**
- | 필드명   | 타입     | 필수 | 설명               |
  |---------|--------|------|------------------|
  | schedule | String | O    | 일정 내용            |
  | password | String | O    | 비밀번호 (삭제/수정 검증용) |
  | email | String | O    | 작성자 이메일          |

- **Body Example (JSON)**

```json
{
  "schedule": "string",
  "password": "string",
  "email": "string"
}
```

- **Response Example**

```json
{
  "data": {
    "id": 0,
    "schedule": "string",
    "writerNm": "string",
    "email": "string",
    "regDt": "string",
    "modDt": "string"
  },
  "result": {
    "status": 200,
    "code": "A000",
    "message": "요청 처리 성공"
  }
}
```

---

### ✅ 2. 일정 목록 조회

- **URL** : `GET /api/schedule`
- **Query Params**
- | 필드 | 타입 | 필요 | 설명 |
  |--------|------|------|------|
  | writerId | Long | 선택 | 작성자 PK |
  | modDt | String(yyyy-MM-dd) | 선택 | 수정일 조회 |
  | page | int | 선택 (기본 1) | 페이지 번호 (1부터) |
  | size | int | 선택 (기본 10) | 페이지 크기 (최대 100) |

- **Response Example**

```json
{
  "data": {
    "data": [
      {
        "id": 0,
        "schedule": "string",
        "writerNm": "string",
        "email": "string",
        "regDt": "yyy-MM-dd HH:mm",
        "modDt": "yyy-MM-dd HH:mm"
      }
    ],
    "total": 0,
    "size": 10,
    "page": 1,
    "totalPages": 0
  },
  "result": {
    "status": 200,
    "code": "A000",
    "message": "요청 처리 성공"
  }
}
```

---

### ✅ 3. 일정 상세 조회

- **URL** : `GET /api/schedule/{id}`

- **Response Example**

```json
{
  "data": {
    "id": 1,
    "schedule": "string",
    "writerNm": "string",
    "email": "string",
    "regDt": "yyy-MM-dd HH:mm",
    "modDt": "yyy-MM-dd HH:mm"
  },
  "result": {
    "status": 200,
    "code": "A000",
    "message": "요청 처리 성공"
  }
}
```

---

### ✅ 4. 일정 수정

- **URL** : `PUT /api/schedule/{id}`
- **Body Example (JSON)**

```json
{
  "schedule": "string",
  "password": "string"
}
```

- **Response Example**

```json
{
  "data": {
    "id": 1,
    "schedule": "string",
    "writerNm": "string",
    "email": "string",
    "regDt": "yyy-MM-dd HH:mm",
    "modDt": "yyy-MM-dd HH:mm"
  },
  "result": {
    "status": 200,
    "code": "A000",
    "message": "요청 처리 성공"
  }
}
```

---

### ✅ 5. 일정 삭제 (Delete Schedule)

- **URL** : `DELETE /api/schedule/{id}`
- **Body Example (JSON)**

```json
{
  "password": "string"
}
```

- ✔ 성공 시 Boolean 반환

```json
{
  "data": true,
  "result": {
    "status": 200,
    "code": "A000",
    "message": "요청 처리 성공"
  }
}
```

---
## 📂 Writer API 명세서
### 🔍 Base URL

```
/api/writer
```

---

### ✅ API 목록 요약

| Method | Endpoint           | 설명        |
|--------|--------------------|-----------|
| POST   | /api/writer        | 작성자 등록    |
| GET    | /api/writer/{id} | 작성자 상세 조회 |

---

### ✅ 1. 일정 등록

- **URL** : `POST /api/writer`
- **요청 Body 필드**
- | 필드명      | 타입     | 필수 | 설명   |
  |----------|--------|------|------|
  | name     | String | O    | 이름  |
  | email    | String | O    | 이메일 |

- **Body Example (JSON)**

```json
{
  "name": "string",
  "email": "string"
}
```

- **Response Example**

```json
{
  "data": {
    "id": 0,
    "name": "string",
    "email": "string",
    "regDt": "yyyy-MM-dd HH:mm:ss",
    "modDt": "yyyy-MM-dd HH:mm:ss"
  },
  "result": {
    "status": 200,
    "code": "A000",
    "message": "요청 처리 성공"
  }
}
```

---
### ✅ 2. 작성자 상세 조회

- **URL** : `GET /api/writer/{id}`

- **Response Example**

```json
{
  "data": {
    "id": 0,
    "name": "string",
    "email": "string",
    "regDt": "yyyy-MM-dd HH:mm:ss",
    "modDt": "yyyy-MM-dd HH:mm:ss"
  },
  "result": {
    "status": 200,
    "code": "A000",
    "message": "요청 처리 성공"
  }
}
```
---
## ⚠️ 개발자 참고 - 예외 처리

| HTTP Status | 설명                     |
|-------------|------------------------|
| 200         | 성공                     |
| 400         | 잘못된 요청 / 파라미터 오류       |
| 401         | 비밀번호 불일치 (PW_MISMATCH) |
| 404         | 데이터 없음                 |
| 500         | 에러                     |

- 📌 모든 실패 응답은 `BaseResponse`로 감싸서 내려감

---

### 📤 공통 응답 형식 (BaseResponse)

```json
{
  "data": {},
  "result": {
    "status": 200,
    "code": "코드",
    "message": "메시지"
  }
}
```

---