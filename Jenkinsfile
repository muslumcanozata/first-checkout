pipeline{
    agent any
    environment {
        IMAGE_NAME = 'muslumcanozata/first-checkout'
        VERSION = 'dev'
        CONTAINER_NAME = 'muslumcanozata/first-checkout:dev'
    }
    stages{
        stage("Destroy Old Container") {
            agent any
            steps{
                script{
                    sh '''
                        CONTAINER_ID=$(docker ps -a | grep ${CONTAINER_NAME} | awk '{ print $1 }')
                        if [ -n "$CONTAINER_ID" ]
                        then
                            docker stop $CONTAINER_ID
                            docker rm -f $CONTAINER_ID
                        fi
                    '''
                }
            }
        }
        stage("Docker Run"){
            agent any
            steps{
                script{
                    withCredentials([usernamePassword(credentialsId: 'dockerhub-user', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                        sh '''
                            docker login -u $USERNAME -p $PASSWORD
                            docker pull ${IMAGE_NAME}:${VERSION}
                            docker run -d -p 8090:8080 --network my-net ${IMAGE_NAME}:${VERSION}
                        '''
                    }
                }
            }
        }
    }
}