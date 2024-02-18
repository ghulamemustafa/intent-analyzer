pipeline {
    agent any

    // Define environment variables
    environment {
        // Define Docker image name
        IMAGE_NAME = 'your-image-name'
        IMAGE_TAG = 'latest'
        REGISTRY_URL = 'your-registry-url'
        REGISTRY_CREDENTIALS_ID = 'your-registry-credentials-id' // Jenkins credentials ID for your registry
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout code from Bitbucket
                checkout scm
            }
        }

        stage('Build with Maven') {
            steps {
                // Run Maven build
                sh 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build Docker image
                    docker.build("${IMAGE_NAME}:${IMAGE_TAG}")
                }
            }
        }

        stage('Push Image to Registry') {
            steps {
                script {
                    // Login to OpenShift's internal Docker registry
                    docker.withRegistry("${REGISTRY_URL}", "${REGISTRY_CREDENTIALS_ID}") {
                        // Push Docker image
                        docker.image("${IMAGE_NAME}:${IMAGE_TAG}").push()
                    }
                }
            }
        }

        stage('Deploy Image') {
            steps {
                script {
                    // This step assumes you have an existing OpenShift project and deployment configured to use the image
                    // Replace 'oc' command options with those appropriate for your OpenShift environment and deployment strategy
                    sh "oc login --token=${OPENSHIFT_TOKEN} --server=${OPENSHIFT_SERVER}"
                    sh "oc project your-openshift-project"
                    sh "oc set image deployment/your-deployment ${IMAGE_NAME}=${REGISTRY_URL}/${IMAGE_NAME}:${IMAGE_TAG} --record"
                }
            }
        }
    }
}
