## setup
```
docker-compose up
```

## test
* list users
```
openstack --os-auth-url http://127.0.0.1:35357/v3 \
    --os-domain-id default \
    --os-project-name library \
    --os-username admin \
    --os-password s3cr3t \
    user list
```
* list role assignments
```
openstack --os-auth-url http://127.0.0.1:35357/v3 \
    --os-domain-id default \
    --os-project-name library \
    --os-username admin \
    --os-password s3cr3t \
    role assignment list --project library --names
```
