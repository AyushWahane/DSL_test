job('MAJOR/PRODUCT_ECOM/webhook') {
    scm {
        git {
            remote {
                url('https://github.com/AyushWahane/product-major.git')
                branch('*/master')
            }
        }
    }
    triggers {
        scm('* * * * *')
    }
    configure { it / 'triggers' / 'com.cloudbees.jenkins.GitHubPushTrigger' / 'spec' }
    steps{
        shell(
            '''pwd'''
        )
    }
}
