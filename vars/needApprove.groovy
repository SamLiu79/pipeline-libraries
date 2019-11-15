#!groovy

def call(body) {
    // evaluate the body block, and collect configuration into the object
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()


    def timeoutTime = config.timeoutTime ?: 24
    def version = config.version ?: "current"
    def proceedMessage = """Would you like to promote ${version} version to the next environment?
"""
    try {
        timeout(time: timeoutTime, unit: 'HOURS') {
            input id: 'Proceed', message: "\n${proceedMessage}"
        }
    } catch (err) {
        throw err
    }
}