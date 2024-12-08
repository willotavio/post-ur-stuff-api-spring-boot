# Post ur stuff

REST API that simulates a social media platform with users and posts.
It uses MongoDB for data persistence and Spring Security with JWT for authentication and authorization.

# Running

After cloning the project, add two files to the resources folder:
- app.pub
- app.key
  
These are the public and private RSA keys used to sign the JWT token for the security layer.
To generate them, run these commands in openssl (you can use git bash):

`openssl genrsa -out keypair.pem 2048`

`openssl rsa -in keypair.pem -pubout -out public.pem`

`openssl pkcs8 -in keypair.pem -topk8 -nocrypt -inform PEM -outform PEM -out private.pem`

public.pem is our app.pub and private.pem is our app.key.

After creating these keys, just add them to the resources folder.

Now you can start the application running `mvn spring-boot:run`