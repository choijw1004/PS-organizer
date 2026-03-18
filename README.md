<p align="center">
  <h1 align="center">PS Organizer</h1>
  <p align="center">
    문제 풀이 레포지토리를 위한 문제 정보 조회 & README 자동 생성 API
    <br />
    BOJ · LeetCode · Programmers 지원
  </p>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-007396?style=flat-square&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring_Boot-4.0.2-6DB33F?style=flat-square&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/Docker-ready-2496ED?style=flat-square&logo=docker&logoColor=white" />
</p>

---

## Overview

**PS Organizer**는 문제 풀이 레포지토리를 관리할 때 필요한 반복 작업을 자동화해주는 REST API입니다.


## Features
- **BOJ 문제 정보 조회**
  - 문제 번호에서 한글 제목 반환
- **LeetCode 문제 정보 조회** 
  - 문제 이름에서 문제 번호 반환
- **README 자동 생성** 
  - 문제 URL + 풀이 메모만 넘기면 디렉토리 이름, 커밋 메시지 생성
- **간편한 실행** 
  — Docker Compose 한 줄로 전체 환경 구동

## Getting Started

### Prerequisites

- Java 21+ 
- Docker & Docker Compose 

### Run with Docker Compose

```bash
docker-compose up
```

| Service | URL |
|---|---|
| core-api | http://localhost:8090 |
| leetcode-api | http://localhost:3030 |


## API Spec

### BOJ

BOJ 문제 번호로 한글 제목을 조회합니다.

**Request**
```
GET /api/boj/{problemId}
```

| 파라미터 | 타입 | 설명 |
|---|---|---|
| `problemId` | `string` | 백준 문제 번호 |

**Response**

| 필드 | 타입 | 설명 |
|---|---|---|
| `problemId` | `integer` | 문제 번호 |
| `titleKo` | `string` | 문제 제목 (한글) |

**Examples**

```bash
GET http://localhost:8090/api/boj/1197
```

```json
{
  "problemId": 1197,
  "titleKo": "최소 스패닝 트리"
}
```

---

### LeetCode

LeetCode 문제 슬러그로 문제 번호를 조회합니다.

**Request**
`GET /api/leet/{titleSlug}`

| 파라미터 | 타입 | 설명 |
|---|---|---|
| `titleSlug` | `string` | 문제 URL의 titleSlug(제목)|


**Response**
| 필드 | 타입 | 설명 |
|---|---|---|
| `questionId` | `string` | 문제 번호 |
| `questionTitle` | `string` | 문제 제목 |

**Examples**

```bash
GET http://localhost:8090/api/leet/Two-Sum
```

```json
{
  "questionId": "1",
  "questionTitle": "Two Sum"
}
```

---

### Organizer

문제 URL과 풀이 정보를 Organizer에게 보내면 디렉토리명, 커밋 메시지, README를 생성하여 반환합니다.

BOJ · LeetCode · Programmers 모두 지원합니다.

**Request**

| 필드 | 타입 | 필수 | 설명 |
|---|---|---|---|
| `problemLink` | `string` | O | 문제 전체 URL |
| `problemCategories` | `string[]` | O | 알고리즘 카테고리 (예: `["DP", "BFS"]`) |
| `problemApproach` | `string` | O | 접근 방식 / 풀이 전략 |

**Response**

| 필드 | 타입 | 설명 |
|---|---|---|
| `directoryName` | `string` | 생성할 폴더명 |
| `commitMessage` | `string` | Git 커밋 메시지 |
| `readmeContent` | `string` | 전체 README.md 내용 |
| `platform` | `string` | `BOJ` / `Leet` / `PGMS` |
| `problemNumber` | `string` | 문제 번호 |
| `problemTitle` | `string` | 문제 제목 |

**Examples**

```json
// Request
{
  "problemLink": "https://www.acmicpc.net/problem/1197",
  "problemCategories": ["MST", "Graph"],
  "problemApproach": "크루스칼 알고리즘으로 최소 신장 트리를 구성했습니다."
}

// Response
{
  "directoryName": "BOJ_1197_최소_스패닝_트리",
  "commitMessage": "feat: BOJ_1197_최소_스패닝_트리",
  "readmeContent": "...",
  "platform": "BOJ",
  "problemNumber": "1197",
  "problemTitle": "최소_스패닝_트리"
}
```

---

## External APIs 

| 플랫폼 | API | 문서 |
|---|---|---|
| BOJ | solved.ac 비공식 API | [문서 보기](https://solvedac.github.io/unofficial-documentation/#/) |
| LeetCode | alfa-leetcode-api | [GitHub](https://github.com/alfaarghya/alfa-leetcode-api) |
| 프로그래머스 | — | URL 파싱만 사용 (외부 API 없음) |



