pipelineJob('MAJOR/PRODUCT_ECOM/Deploy') {
    description('Product ecom deploy job')
    triggers {
        upstream('Build', 'STABLE')
    }
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
        stage('Pull image') {
            steps{
                sh """
                    docker login -u krishna1708 -p Abcd97@4321
                    docker pull krishna1708/product-major:latest
                """
            }
        }   
        stage('deploy') {
            steps{
                sh """if [ "\$(docker ps -q -f name=web2)" ]; then
	docker stop web2
	docker rm web2
	docker rmi krishna1708/product-major:latest
fi

docker run -d -p 4002:80 --name web2 krishna1708/product-major:latest
                """
            }
        }
        
        
        stage('Check Deployment') {
            steps{
                sh """if [ ! "\$(docker inspect -f {{.State.Running}} web2)" ]; then
	exit 1	
fi

docker ps -a
                """
            }
        }
    }
}''')
            sandbox()
        }
    }
}
