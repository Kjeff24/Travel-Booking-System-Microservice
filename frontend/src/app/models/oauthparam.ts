export class Oauthparam {
    client_id: string;
    redirect_uri: string
    scope: string
    response_type: string
    response_mode: string
    code_challenge_method: string
    code_challenge: string

    constructor(){
        this.client_id = '',
        this.redirect_uri = '',
        this.scope = '',
        this.response_type = '',
        this.response_mode = '',
        this.code_challenge_method = '',
        this.code_challenge = ''
    }
}
