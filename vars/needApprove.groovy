#!groovy

// @timeoutTime: how many hours to wait approve, default is 24hours
// @processMessage: message to show.
// @approveSubmitter: user or group list can approval
def call(body) {
    // evaluate the body block, and collect configuration into the object
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()


    def timeoutTime = config.timeoutTime ?: 24*30
    def processMessage = config.processMessage ?: """Would you like to approve this?
"""
    def approveSubmitter = config.approveSubmitter ?: ''
    try {
        timeout(time: timeoutTime, unit: 'HOURS') {
            input id: 'Proceed', message: "\n${processMessage}", submitter: "${approveSubmitter}"
        }
    } catch (err) {
        throw err
    }
}