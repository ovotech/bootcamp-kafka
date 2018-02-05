## Requirements
TODO
 - docker
 
 
 ### Testing with CURL
 
 - Create a customer
 ````bash
 curl -d '{"name": "Filippo De Luca", "emailAddress": "filippo.deluca@ovoenergy.com" }'  -H "Content-Type: application/json" -X POST http://localhost:8080/api/v1/customer
 ````