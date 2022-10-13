const { verify } = require('jsonwebtoken');
const jwkToPem = require('jwk-to-pem');
const fetch = require('node-fetch');

const jwkUrl = process.argv[2];
const token = process.argv[3];

fetch(jwkUrl)
.then(response => response.json())
.then(json => {
  const pem = jwkToPem(json.keys[1]);
  verify(token, pem, function(err, decodedToken) {
    if (!err == null) {
      console.log(err)
    } else {
      console.log(decodedToken)
    }
  });
})
.catch(error => {
  console.log(error);
});