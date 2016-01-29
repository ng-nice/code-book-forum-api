class BootStrap {
  def setupService;
  def init = { servletContext ->
    setupService.init();
  }
  def destroy = {
  }
}
