pipelineJob('MAJOR/PRODUCT_ECOM/Build') {
    description('Product ecom build job')
    triggers {
        upstream('webhook', 'UNSTABLE')
    }
    configure { it / 'triggers' / 'com.cloudbees.jenkins.GitHubPushTrigger' / 'spec' }
    definition {
        cps {
            script('''pipeline {
    environment {
        registry = "krishna1708/"
        registryCredential = ''
        dockerImage = ''
    }
    agent {
    node {
        label 'slave'
        }
    }
    stages {
        stage('Clone Git Repository') {
            steps{
                sh """
                    rm -rf ./product-major
                    git clone --branch master https://github.com/AyushWahane/product-major.git
                    
                """
            }
        }   
        stage('Building image') {
            steps{
                sh """
                    cd product-major
                    sudo docker login -u krishna1708 -p Abcd97@4321
                    sudo docker build -t krishna1708/product-major:latest .
                """
            }
        }
        
        
        stage('Push image') {
            steps{
                sh """
                    sudo docker login -u krishna1708 -p Abcd97@4321
                    sudo docker push krishna1708/product-major:latest
                """
            }
        }
        
        stage('Cleaning up') {
            steps{
                sh """
                    sudo docker rmi krishna1708/product-major:latest
                """
            }
        }
    }
}
            ''')
            sandbox()
        }
    }
}
