<!-- Generator: Widdershins v4.0.1 -->

<h1 id="m295">M295 v1.0.0</h1>

> Scroll down for code samples, example requests and responses. Select a language for code samples from the tabs above or the mobile navigation menu.

M295

Base URLs:

* <a href="http://localhost:8080">http://localhost:8080</a>

# Authentication

- HTTP Authentication, scheme: basic Use `user` / `password` as the test credentials

<h1 id="m295-team">team</h1>

## getTeams

<a id="opIdgetTeams"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/team \
  -H 'Accept: application/json'

```

`GET /team`

*Get all teams*

Get all teams

> Example responses

> 200 Response

```json
[
  {
    "teamId": 10,
    "budget": 96,
    "teamName": "Team 1",
    "teamMembers": [
      {
        "memberId": 10,
        "name": "Smith",
        "firstname": "John",
        "joinDate": "2019-08-24T14:15:22Z",
        "teamId": 0
      }
    ]
  }
]
```

<h3 id="getteams-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Successful operation|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid Request supplied|None|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Teams not found|None|
|405|[Method Not Allowed](https://tools.ietf.org/html/rfc7231#section-6.5.5)|Validation exception|None|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Critical error|None|

<h3 id="getteams-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[Team](#schemateam)]|false|none|none|
|» teamId|integer(int32)|false|none|none|
|» budget|number(double)|true|none|none|
|» teamName|string|true|none|none|
|» teamMembers|[[TeamMember](#schemateammember)]|true|none|none|
|»» memberId|integer(int32)|false|none|none|
|»» name|string|true|none|none|
|»» firstname|string|true|none|none|
|»» joinDate|string(date-time)|true|none|none|
|»» teamId|integer(int32)|false|none|none|

<aside class="success">
This operation does not require authentication
</aside>

## createTeam

<a id="opIdcreateTeam"></a>

> Code samples

```shell
# You can also use wget
curl -X POST http://localhost:8080/team \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json'

```

`POST /team`

*Create a team*

Create a team

> Body parameter

```json
{
  "teamName": "Team 1",
  "budget": 96
}
```

<h3 id="createteam-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[TeamRequest](#schemateamrequest)|false|none|

> Example responses

> 200 Response

```json
0
```

<h3 id="createteam-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Successful operation|integer|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid Request supplied|None|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Critical error|None|

<aside class="success">
This operation does not require authentication
</aside>

## getTeam

<a id="opIdgetTeam"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/team/admin/{teamId} \
  -H 'Accept: application/json'

```

`GET /team/admin/{teamId}`

*Get one team*

Get one team

<h3 id="getteam-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|teamId|path|integer(int32)|true|teamId|

> Example responses

> 200 Response

```json
{
  "teamId": 10,
  "budget": 96,
  "teamName": "Team 1",
  "teamMembers": [
    {
      "memberId": 10,
      "name": "Smith",
      "firstname": "John",
      "joinDate": "2019-08-24T14:15:22Z",
      "teamId": 0
    }
  ]
}
```

<h3 id="getteam-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Successful operation|[Team](#schemateam)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid Request supplied|None|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Critical error|None|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
basicAuth
</aside>

## updateTeam

<a id="opIdupdateTeam"></a>

> Code samples

```shell
# You can also use wget
curl -X PUT http://localhost:8080/team/admin/{teamId} \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json'

```

`PUT /team/admin/{teamId}`

*Update a team*

Update a team

> Body parameter

```json
{
  "teamName": "Team 1",
  "budget": 96
}
```

<h3 id="updateteam-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|teamId|path|integer(int32)|true|teamId|
|body|body|[TeamRequest](#schemateamrequest)|false|none|

> Example responses

> 200 Response

```json
{
  "teamId": 10,
  "budget": 96,
  "teamName": "Team 1",
  "teamMembers": [
    {
      "memberId": 10,
      "name": "Smith",
      "firstname": "John",
      "joinDate": "2019-08-24T14:15:22Z",
      "teamId": 0
    }
  ]
}
```

<h3 id="updateteam-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Successful operation|[Team](#schemateam)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid Request supplied|None|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Critical error|None|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
basicAuth
</aside>

## deleteTeam

<a id="opIddeleteTeam"></a>

> Code samples

```shell
# You can also use wget
curl -X DELETE http://localhost:8080/team/admin/{teamId} \
  -H 'Accept: application/json'

```

`DELETE /team/admin/{teamId}`

*Delete a team*

Delete a team

<h3 id="deleteteam-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|teamId|path|integer(int32)|true|teamId|

> Example responses

> 200 Response

```json
{
  "teamId": 10,
  "budget": 96,
  "teamName": "Team 1",
  "teamMembers": [
    {
      "memberId": 10,
      "name": "Smith",
      "firstname": "John",
      "joinDate": "2019-08-24T14:15:22Z",
      "teamId": 0
    }
  ]
}
```

<h3 id="deleteteam-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Successful operation|[Team](#schemateam)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid Request supplied|None|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Critical error|None|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
basicAuth
</aside>

<h1 id="m295-teammember">teamMember</h1>

## getTeamMembers

<a id="opIdgetTeamMembers"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/teamMember \
  -H 'Accept: application/json'

```

`GET /teamMember`

*Get all teamMembers*

Get all teamMembers

> Example responses

> 200 Response

```json
[
  {
    "memberId": 10,
    "name": "Smith",
    "firstname": "John",
    "joinDate": "2019-08-24T14:15:22Z",
    "teamId": 0
  }
]
```

<h3 id="getteammembers-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Successful operation|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid Request supplied|None|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|TeamMembers not found|None|
|405|[Method Not Allowed](https://tools.ietf.org/html/rfc7231#section-6.5.5)|Validation exception|None|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Critical error|None|

<h3 id="getteammembers-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[TeamMember](#schemateammember)]|false|none|none|
|» memberId|integer(int32)|false|none|none|
|» name|string|true|none|none|
|» firstname|string|true|none|none|
|» joinDate|string(date-time)|true|none|none|
|» teamId|integer(int32)|false|none|none|

<aside class="success">
This operation does not require authentication
</aside>

## createTeamMember

<a id="opIdcreateTeamMember"></a>

> Code samples

```shell
# You can also use wget
curl -X POST http://localhost:8080/teamMember \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json'

```

`POST /teamMember`

*Create a teamMember*

Create a teamMember

> Body parameter

```json
{
  "name": "Smith",
  "firstname": "John",
  "joinDate": "2019-08-24T14:15:22Z",
  "teamId": 10
}
```

<h3 id="createteammember-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[TeamMemberRequest](#schemateammemberrequest)|false|none|

> Example responses

> 200 Response

```json
0
```

<h3 id="createteammember-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Successful operation|integer|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid Request supplied|None|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Critical error|None|

<aside class="success">
This operation does not require authentication
</aside>

## getTeamMember

<a id="opIdgetTeamMember"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/teamMember/admin/{memberId} \
  -H 'Accept: application/json'

```

`GET /teamMember/admin/{memberId}`

*Get one teamMember*

Get one teamMember

<h3 id="getteammember-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|memberId|path|integer(int32)|true|memberId|

> Example responses

> 200 Response

```json
{
  "memberId": 10,
  "name": "Smith",
  "firstname": "John",
  "joinDate": "2019-08-24T14:15:22Z",
  "teamId": 0
}
```

<h3 id="getteammember-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Successful operation|[TeamMember](#schemateammember)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid Request supplied|None|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Critical error|None|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
basicAuth
</aside>

## updateTeamMember

<a id="opIdupdateTeamMember"></a>

> Code samples

```shell
# You can also use wget
curl -X PUT http://localhost:8080/teamMember/admin/{memberId} \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json'

```

`PUT /teamMember/admin/{memberId}`

*Update a teamMember*

Update a teamMember

> Body parameter

```json
{
  "name": "Smith",
  "firstname": "John",
  "joinDate": "2019-08-24T14:15:22Z",
  "teamId": 10
}
```

<h3 id="updateteammember-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|memberId|path|integer(int32)|true|memberId|
|body|body|[TeamMemberRequest](#schemateammemberrequest)|false|none|

> Example responses

> 200 Response

```json
{
  "memberId": 10,
  "name": "Smith",
  "firstname": "John",
  "joinDate": "2019-08-24T14:15:22Z",
  "teamId": 0
}
```

<h3 id="updateteammember-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Successful operation|[TeamMember](#schemateammember)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid Request supplied|None|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Critical error|None|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
basicAuth
</aside>

## deleteTeamMember

<a id="opIddeleteTeamMember"></a>

> Code samples

```shell
# You can also use wget
curl -X DELETE http://localhost:8080/teamMember/admin/{memberId} \
  -H 'Accept: application/json'

```

`DELETE /teamMember/admin/{memberId}`

*Delete a teamMember*

Delete a teamMember

<h3 id="deleteteammember-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|memberId|path|integer(int32)|true|memberId|

> Example responses

> 200 Response

```json
{
  "memberId": 10,
  "name": "Smith",
  "firstname": "John",
  "joinDate": "2019-08-24T14:15:22Z",
  "teamId": 0
}
```

<h3 id="deleteteammember-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Successful operation|[TeamMember](#schemateammember)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid Request supplied|None|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Critical error|None|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
basicAuth
</aside>

<h1 id="m295-software">software</h1>

## getSoftwares

<a id="opIdgetSoftwares"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/software \
  -H 'Accept: application/json'

```

`GET /software`

*Get all software*

Get all software

> Example responses

> 200 Response

```json
[
  {
    "name": "Software 1",
    "description": "Software 1 description",
    "version": "1.0.0",
    "teamId": 1
  },
  {
    "name": "Software 2",
    "description": "Software 2 description",
    "version": "1.0.0",
    "teamId": 1
  }
]
```

<h3 id="getsoftwares-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Successful operation|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid Request supplied|None|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Software not found|None|
|405|[Method Not Allowed](https://tools.ietf.org/html/rfc7231#section-6.5.5)|Validation exception|None|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Critical error|None|

<h3 id="getsoftwares-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[Software](#schemasoftware)]|false|none|none|
|» softwareId|string|false|none|none|
|» softwareName|string|true|none|none|
|» softwareVersion|string|true|none|none|
|» team|[Team](#schemateam)|true|none|none|
|»» teamId|integer(int32)|false|none|none|
|»» budget|number(double)|true|none|none|
|»» teamName|string|true|none|none|
|»» teamMembers|[[TeamMember](#schemateammember)]|true|none|none|
|»»» memberId|integer(int32)|false|none|none|
|»»» name|string|true|none|none|
|»»» firstname|string|true|none|none|
|»»» joinDate|string(date-time)|true|none|none|
|»»» teamId|integer(int32)|false|none|none|
|» project|[Project](#schemaproject)|true|none|none|
|»» projectId|integer(int32)|false|none|none|
|»» projectName|string|true|none|none|
|» status|string|true|none|Order Status|

#### Enumerated Values

|Property|Value|
|---|---|
|status|deployed|
|status|testing|
|status|development|
|status|planning|

<aside class="success">
This operation does not require authentication
</aside>

## createSoftware

<a id="opIdcreateSoftware"></a>

> Code samples

```shell
# You can also use wget
curl -X POST http://localhost:8080/software \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json'

```

`POST /software`

*Create a software*

Create a software

> Body parameter

```json
{
  "softwareId": "AOP",
  "softwareName": "Application One Project",
  "softwareVersion": "1.0.0",
  "teamId": 10,
  "projectId": 10,
  "status": "approved"
}
```

<h3 id="createsoftware-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[SoftwareRequest](#schemasoftwarerequest)|false|none|

> Example responses

> 200 Response

```json
0
```

<h3 id="createsoftware-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Successful operation|integer|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid Request supplied|None|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Critical error|None|

<aside class="success">
This operation does not require authentication
</aside>

## getSoftware

<a id="opIdgetSoftware"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/software/admin/{softwareId} \
  -H 'Accept: application/json'

```

`GET /software/admin/{softwareId}`

*Get one software*

Get one software

<h3 id="getsoftware-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|softwareId|path|string|true|softwareId|

> Example responses

> 200 Response

```json
{
  "softwareId": "AOP",
  "softwareName": "Application One Project",
  "softwareVersion": "1.0.0",
  "team": {
    "teamId": 10,
    "budget": 96,
    "teamName": "Team 1",
    "teamMembers": [
      {
        "memberId": 10,
        "name": "Smith",
        "firstname": "John",
        "joinDate": "2019-08-24T14:15:22Z",
        "teamId": 0
      }
    ]
  },
  "project": {
    "projectId": 10,
    "projectName": "Project 1"
  },
  "status": "approved"
}
```

<h3 id="getsoftware-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Successful operation|[Software](#schemasoftware)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid Request supplied|None|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Critical error|None|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
basicAuth
</aside>

## updateSoftware

<a id="opIdupdateSoftware"></a>

> Code samples

```shell
# You can also use wget
curl -X PUT http://localhost:8080/software/admin/{softwareId} \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json'

```

`PUT /software/admin/{softwareId}`

*Update a software*

Update a software

> Body parameter

```json
{
  "softwareId": "AOP",
  "softwareName": "Application One Project",
  "softwareVersion": "1.0.0",
  "teamId": 10,
  "projectId": 10,
  "status": "approved"
}
```

<h3 id="updatesoftware-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|softwareId|path|string|true|softwareId|
|body|body|[SoftwareRequest](#schemasoftwarerequest)|false|none|

> Example responses

> 200 Response

```json
{
  "softwareId": "AOP",
  "softwareName": "Application One Project",
  "softwareVersion": "1.0.0",
  "team": {
    "teamId": 10,
    "budget": 96,
    "teamName": "Team 1",
    "teamMembers": [
      {
        "memberId": 10,
        "name": "Smith",
        "firstname": "John",
        "joinDate": "2019-08-24T14:15:22Z",
        "teamId": 0
      }
    ]
  },
  "project": {
    "projectId": 10,
    "projectName": "Project 1"
  },
  "status": "approved"
}
```

<h3 id="updatesoftware-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Successful operation|[Software](#schemasoftware)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid Request supplied|None|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Critical error|None|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
basicAuth
</aside>

## deleteSoftware

<a id="opIddeleteSoftware"></a>

> Code samples

```shell
# You can also use wget
curl -X DELETE http://localhost:8080/software/admin/{softwareId} \
  -H 'Accept: application/json'

```

`DELETE /software/admin/{softwareId}`

*Delete a software*

Delete a software

<h3 id="deletesoftware-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|softwareId|path|string|true|softwareId|

> Example responses

> 200 Response

```json
{
  "softwareId": "AOP",
  "softwareName": "Application One Project",
  "softwareVersion": "1.0.0",
  "team": {
    "teamId": 10,
    "budget": 96,
    "teamName": "Team 1",
    "teamMembers": [
      {
        "memberId": 10,
        "name": "Smith",
        "firstname": "John",
        "joinDate": "2019-08-24T14:15:22Z",
        "teamId": 0
      }
    ]
  },
  "project": {
    "projectId": 10,
    "projectName": "Project 1"
  },
  "status": "approved"
}
```

<h3 id="deletesoftware-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Successful operation|[Software](#schemasoftware)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid Request supplied|None|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Critical error|None|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
basicAuth
</aside>

<h1 id="m295-project">project</h1>

## getProjects

<a id="opIdgetProjects"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/project \
  -H 'Accept: application/json'

```

`GET /project`

*Get all projects*

Get all projects

> Example responses

> 200 Response

```json
[
  {
    "projectId": 10,
    "projectName": "Project 1"
  }
]
```

<h3 id="getprojects-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Successful operation|Inline|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid Request supplied|None|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Project not found|None|
|405|[Method Not Allowed](https://tools.ietf.org/html/rfc7231#section-6.5.5)|Validation exception|None|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Critical error|None|

<h3 id="getprojects-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[Project](#schemaproject)]|false|none|none|
|» projectId|integer(int32)|false|none|none|
|» projectName|string|true|none|none|

<aside class="success">
This operation does not require authentication
</aside>

## createProject

<a id="opIdcreateProject"></a>

> Code samples

```shell
# You can also use wget
curl -X POST http://localhost:8080/project \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json'

```

`POST /project`

*Create a project*

Create a project

> Body parameter

```json
{
  "projectName": "Project 1"
}
```

<h3 id="createproject-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[ProjectRequest](#schemaprojectrequest)|false|none|

> Example responses

> 200 Response

```json
0
```

<h3 id="createproject-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Successful operation|integer|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid Request supplied|None|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Critical error|None|

<aside class="success">
This operation does not require authentication
</aside>

## getProject

<a id="opIdgetProject"></a>

> Code samples

```shell
# You can also use wget
curl -X GET http://localhost:8080/project/admin/{projectId} \
  -H 'Accept: application/json'

```

`GET /project/admin/{projectId}`

*Get one project*

Get one project

<h3 id="getproject-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|projectId|path|integer(int32)|true|projectId|

> Example responses

> 200 Response

```json
{
  "projectId": 10,
  "projectName": "Project 1"
}
```

<h3 id="getproject-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Successful operation|[Project](#schemaproject)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid Request supplied|None|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Critical error|None|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
basicAuth
</aside>

## updateProject

<a id="opIdupdateProject"></a>

> Code samples

```shell
# You can also use wget
curl -X PUT http://localhost:8080/project/admin/{projectId} \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json'

```

`PUT /project/admin/{projectId}`

*Update a project*

Update a project

> Body parameter

```json
{
  "projectName": "Project 1"
}
```

<h3 id="updateproject-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|projectId|path|integer(int32)|true|projectId|
|body|body|[ProjectRequest](#schemaprojectrequest)|false|none|

> Example responses

> 200 Response

```json
{
  "projectId": 10,
  "projectName": "Project 1"
}
```

<h3 id="updateproject-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Successful operation|[Project](#schemaproject)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid Request supplied|None|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Critical error|None|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
basicAuth
</aside>

## deleteProject

<a id="opIddeleteProject"></a>

> Code samples

```shell
# You can also use wget
curl -X DELETE http://localhost:8080/project/admin/{projectId} \
  -H 'Accept: application/json'

```

`DELETE /project/admin/{projectId}`

*Delete a project*

Delete a project

<h3 id="deleteproject-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|projectId|path|integer(int32)|true|projectId|

> Example responses

> 200 Response

```json
{
  "projectId": 10,
  "projectName": "Project 1"
}
```

<h3 id="deleteproject-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Successful operation|[Project](#schemaproject)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Invalid Request supplied|None|
|500|[Internal Server Error](https://tools.ietf.org/html/rfc7231#section-6.6.1)|Critical error|None|

<aside class="warning">
To perform this operation, you must be authenticated by means of one of the following methods:
basicAuth
</aside>

# Schemas

<h2 id="tocS_TeamMember">TeamMember</h2>
<!-- backwards compatibility -->
<a id="schemateammember"></a>
<a id="schema_TeamMember"></a>
<a id="tocSteammember"></a>
<a id="tocsteammember"></a>

```json
{
  "memberId": 10,
  "name": "Smith",
  "firstname": "John",
  "joinDate": "2019-08-24T14:15:22Z",
  "teamId": 0
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|memberId|integer(int32)|false|none|none|
|name|string|true|none|none|
|firstname|string|true|none|none|
|joinDate|string(date-time)|true|none|none|
|teamId|integer(int32)|false|none|none|

<h2 id="tocS_Team">Team</h2>
<!-- backwards compatibility -->
<a id="schemateam"></a>
<a id="schema_Team"></a>
<a id="tocSteam"></a>
<a id="tocsteam"></a>

```json
{
  "teamId": 10,
  "budget": 96,
  "teamName": "Team 1",
  "teamMembers": [
    {
      "memberId": 10,
      "name": "Smith",
      "firstname": "John",
      "joinDate": "2019-08-24T14:15:22Z",
      "teamId": 0
    }
  ]
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|teamId|integer(int32)|false|none|none|
|budget|number(double)|true|none|none|
|teamName|string|true|none|none|
|teamMembers|[[TeamMember](#schemateammember)]|true|none|none|

<h2 id="tocS_Software">Software</h2>
<!-- backwards compatibility -->
<a id="schemasoftware"></a>
<a id="schema_Software"></a>
<a id="tocSsoftware"></a>
<a id="tocssoftware"></a>

```json
{
  "softwareId": "AOP",
  "softwareName": "Application One Project",
  "softwareVersion": "1.0.0",
  "team": {
    "teamId": 10,
    "budget": 96,
    "teamName": "Team 1",
    "teamMembers": [
      {
        "memberId": 10,
        "name": "Smith",
        "firstname": "John",
        "joinDate": "2019-08-24T14:15:22Z",
        "teamId": 0
      }
    ]
  },
  "project": {
    "projectId": 10,
    "projectName": "Project 1"
  },
  "status": "approved"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|softwareId|string|false|none|none|
|softwareName|string|true|none|none|
|softwareVersion|string|true|none|none|
|team|[Team](#schemateam)|true|none|none|
|project|[Project](#schemaproject)|true|none|none|
|status|string|true|none|Order Status|

#### Enumerated Values

|Property|Value|
|---|---|
|status|deployed|
|status|testing|
|status|development|
|status|planning|

<h2 id="tocS_Project">Project</h2>
<!-- backwards compatibility -->
<a id="schemaproject"></a>
<a id="schema_Project"></a>
<a id="tocSproject"></a>
<a id="tocsproject"></a>

```json
{
  "projectId": 10,
  "projectName": "Project 1"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|projectId|integer(int32)|false|none|none|
|projectName|string|true|none|none|

<h2 id="tocS_TeamRequest">TeamRequest</h2>
<!-- backwards compatibility -->
<a id="schemateamrequest"></a>
<a id="schema_TeamRequest"></a>
<a id="tocSteamrequest"></a>
<a id="tocsteamrequest"></a>

```json
{
  "teamName": "Team 1",
  "budget": 96
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|teamName|string|true|none|none|
|budget|number(double)|true|none|none|

<h2 id="tocS_SoftwareRequest">SoftwareRequest</h2>
<!-- backwards compatibility -->
<a id="schemasoftwarerequest"></a>
<a id="schema_SoftwareRequest"></a>
<a id="tocSsoftwarerequest"></a>
<a id="tocssoftwarerequest"></a>

```json
{
  "softwareId": "AOP",
  "softwareName": "Application One Project",
  "softwareVersion": "1.0.0",
  "teamId": 10,
  "projectId": 10,
  "status": "approved"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|softwareId|string|true|none|none|
|softwareName|string|true|none|none|
|softwareVersion|string|true|none|none|
|teamId|integer(int32)|true|none|none|
|projectId|integer(int32)|true|none|none|
|status|string|true|none|Order Status|

#### Enumerated Values

|Property|Value|
|---|---|
|status|deployed|
|status|testing|
|status|development|
|status|planning|

<h2 id="tocS_ProjectRequest">ProjectRequest</h2>
<!-- backwards compatibility -->
<a id="schemaprojectrequest"></a>
<a id="schema_ProjectRequest"></a>
<a id="tocSprojectrequest"></a>
<a id="tocsprojectrequest"></a>

```json
{
  "projectName": "Project 1"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|projectName|string|true|none|none|

<h2 id="tocS_TeamMemberRequest">TeamMemberRequest</h2>
<!-- backwards compatibility -->
<a id="schemateammemberrequest"></a>
<a id="schema_TeamMemberRequest"></a>
<a id="tocSteammemberrequest"></a>
<a id="tocsteammemberrequest"></a>

```json
{
  "name": "Smith",
  "firstname": "John",
  "joinDate": "2019-08-24T14:15:22Z",
  "teamId": 10
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|name|string|true|none|none|
|firstname|string|true|none|none|
|joinDate|string(date-time)|true|none|none|
|teamId|integer(int32)|true|none|none|

