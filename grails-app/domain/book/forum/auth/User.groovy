package book.forum.auth

import book.forum.person.Person

class User {
  transient springSecurityService

  String username
  String password
  boolean enabled = false
  boolean accountExpired = false
  boolean accountLocked = false
  boolean passwordExpired = false

  Person person
  static belongsTo = [person: Person]

  static transients = ['springSecurityService']

  static constraints = {
    username blank: false, unique: true, size: 6..32
    password blank: false, size: 6..128
  }

  static mapping = {
    password column: '`password`'
  }

  Set<Role> getAuthorities() {
    UserRole.findAllByUser(this).collect { it.role }
  }

  def beforeInsert() {
    encodePassword()
  }

  def beforeUpdate() {
    if (isDirty('password')) {
      encodePassword()
    }
  }

  protected void encodePassword() {
    password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
  }
}
