export const environment = {
    authorize_uri: 'http://localhost:8080/oauth2/authorize?',
    client_id : 'angular-client',
    redirect_uri: 'http://127.0.0.1:4200/login/oauth2/code/angular-client',
    scope: 'openid profile',
    response_type: 'code',
    response_mode: 'form_post',
    code_challenge_method: 'S256',
    token_url: 'http://localhost:8080/oauth2/token',
    grant_type: 'authorization_code',
    booking_service_url: 'http://localhost:8081/api/booking-service',
    logout_url: 'http://localhost:8080/logout',
    signup_url: 'http://localhost:8080/signup',
    secret_pkce: 'secret'
};
