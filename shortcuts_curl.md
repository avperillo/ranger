```shell
curl -X POST \
  http://localhost:6080/service/policies/importPoliciesFromFile \
  -H 'Content-Type: multipart/form-data' \
  -F 'servicesMapJson=@path/to/servicesMap.json' \
  -F 'zoneMapJson=@path/to/zoneMap.json' \
  -F 'file=@path/to/file' \
  -F 'isOverride=true' \
  -F 'importType=someType'
```

```shell
curl -X GET \
  http://localhost:6080/service/policies/importPoliciesFromFile \
  -H 'Content-Type: multipart/form-data' \
  -F 'servicesMapJson=@path/to/servicesMap.json' \
  -F 'zoneMapJson=@path/to/zoneMap.json' \
  -F 'file=@path/to/file' \
  -F 'isOverride=true' \
  -F 'importType=someType'
```