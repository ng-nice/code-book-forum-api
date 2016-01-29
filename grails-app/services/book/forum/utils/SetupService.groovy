package book.forum.utils

import book.forum.auth.Requestmap
import book.forum.auth.Role
import book.forum.auth.UserRole
import book.forum.auth.ZRoleNames
import book.forum.person.Author
import book.forum.person.ZSexType
import grails.transaction.Transactional
import org.springframework.http.HttpMethod

@Transactional
class SetupService {
  def springSecurityService;

  def init() {
    def roleAdmin = Role.findOrSaveWhere(authority: ZRoleNames.ROLE_Admin.name());
    def roleAuthor = Role.findOrSaveWhere(authority: ZRoleNames.ROLE_Author.name());
    def roleReader = Role.findOrSaveWhere(authority: ZRoleNames.ROLE_Reader.name());

    Requestmap.findOrSaveWhere(configAttribute: ZRoleNames.IS_AUTHENTICATED_ANONYMOUSLY.name(), httpMethod: HttpMethod.POST, url: '/login/auth');
    Requestmap.findOrSaveWhere(configAttribute: ZRoleNames.IS_AUTHENTICATED_ANONYMOUSLY.name(), httpMethod: HttpMethod.POST, url: '/readers');
    Requestmap.findOrSaveWhere(configAttribute: ZRoleNames.ROLE_Admin.name(), httpMethod: HttpMethod.GET, url: '/users');
    Requestmap.findOrSaveWhere(configAttribute: ZRoleNames.ROLE_Admin.name(), httpMethod: HttpMethod.GET, url: '/readers');


    if (!Author.findByEmail('xuelang@gmail.com')) {
      def snow = new Author(nickname: '雪狼', email: 'xuelang@mail.com', sexType: ZSexType.Male);
      snow.addToUsers(username: 'xuelang', password: 'testtest', enabled: true);
      snow.save(failOnError: true);
      def white = new Author(nickname: '破狼', email: 'polang@mail.com', sexType: ZSexType.Male);
      white.addToUsers(username: 'polang', password: 'testtest', enabled: true);
      white.save(flush: true);

      UserRole.create(snow.users[0], roleAdmin, false);
      UserRole.create(white.users[0], roleAdmin, false);
    }

    springSecurityService.clearCachedRequestmaps();
  }
}
