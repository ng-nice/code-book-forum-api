package book.forum.utils

import book.forum.auth.*
import book.forum.person.Author
import book.forum.person.ZSexType
import grails.transaction.Transactional
import org.springframework.http.HttpMethod

@Transactional
class SetupService {
    def springSecurityService;

    def init() {
        def snow = new Author(nickname: '雪狼', email: 'xuelang@mail.com', sexType: ZSexType.Male);
        snow.addToUsers(username: 'xuelang', password: 'testtest', enabled: true);
        snow.save(failOnError: true);
        def white = new Author(nickname: '破狼', email: 'polang@mail.com', sexType: ZSexType.Male);
        white.addToUsers(username: 'polang', password: 'testtest', enabled: true);
        white.save(flush: true);

        def roleAdmin = Role.findOrSaveWhere(authority: ZRoleNames.ROLE_Admin.name());
        def roleAuthor = Role.findOrSaveWhere(authority: ZRoleNames.ROLE_Author.name());
        def roleReader = Role.findOrSaveWhere(authority: ZRoleNames.ROLE_Reader.name());

        UserRole.create(snow.users[0], roleAdmin, false);
        UserRole.create(white.users[0], roleAdmin, false);

        Requestmap.findOrSaveWhere(configAttribute: ZRoleNames.IS_AUTHENTICATED_ANONYMOUSLY.name(), httpMethod: HttpMethod.GET, url: '/login/auth');
        Requestmap.findOrSaveWhere(configAttribute: ZRoleNames.IS_AUTHENTICATED_ANONYMOUSLY.name(), httpMethod: HttpMethod.POST, url: '/readers');
        Requestmap.findOrSaveWhere(configAttribute: ZRoleNames.ROLE_Admin.name(), httpMethod: HttpMethod.GET, url: '/users');

        springSecurityService.clearCachedRequestmaps();
    }
}
