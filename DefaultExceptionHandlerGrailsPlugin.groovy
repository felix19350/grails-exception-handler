import  org.grails.plugins.defaultExceptionHandler.NullDefaultExceptionHandlerProcessor

class DefaultExceptionHandlerGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.0 > *"
    // the other plugins this plugin depends on
    def dependsOn = [quartz2: "0.2.3 > *"]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Default Exception Handler Plugin" // Headline display name of the plugin
    def author = "Your name"
    def authorEmail = ""
    def description = '''\
Brief summary/description of the plugin.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/default-exception-handler"
    
    def loadBefore = ['controllers']

	def doWithSpring = {
        if(! delegate.containsBeanDefinition("defaultExceptionHandlerProcessor")) {
           defaultExceptionHandlerProcessor(NullDefaultExceptionHandlerProcessor)
        }
    }
}
