package book.forum.auth

import grails.validation.ValidationException
import org.springframework.http.HttpStatus

class LoginController {
    static responseFormats = ['json', 'xml']
    static allowedMethods = [index: "POST"]

    static defaultAction = 'index';
    def springSecurityService;

    def index(CredentialCommand credential) {
        if (!credential.validate()) {
            throw new ValidationException('', credential.errors);
        }
        request.login(credential.username, credential.password);
        forward(action: 'success');
    }

    def auth() {
        render(status: HttpStatus.UNAUTHORIZED, text: 'Unauthorized');
    }

    def success() {
        respond(name: request.remoteUser, roles: request.userPrincipal.authorities*.authority);
    }

    def failed() {
        render(status: HttpStatus.UNAUTHORIZED, text: 'BadCredential');
    }

    def denied() {
        render(status: HttpStatus.FORBIDDEN, text: 'Forbidden');
    }
}
