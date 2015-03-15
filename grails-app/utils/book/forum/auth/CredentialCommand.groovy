package book.forum.auth

import grails.validation.Validateable

@Validateable
class CredentialCommand {
    String username;
    String password;
    static constraints = {
        username(nullable: false, blank: false);
        password(nullable: false, blank: false);
    }
}