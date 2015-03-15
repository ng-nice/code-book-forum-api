class UrlMappings {
  static mappings = {
    "/setup/$action"(controller: 'setup')
    "/login/$action"(controller: 'login')
    "/logout"(controller: 'logout')
    "/readers"(resources: 'reader')
    "/users"(resources: 'user')
    "/"(view: "/index")
    "500"(controller: 'error', action: 'internal')
  }
}
