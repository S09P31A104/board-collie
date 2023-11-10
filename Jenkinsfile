pipeline {
    agent any

    tools {
        dockerTool 'Docker'
    }

    stages {
        stage("Clear current directory") {
            steps {
                sh 'rm -rf *'
            }
        }

        stage('Pull from GitLab') {
            steps {
                git url: 'https://lab.ssafy.com/s09-final/S09P31A104.git',
                    branch: 'rec/develop',
                    credentialsId: 'somin'
            }
        }
        stage('Setup Python Environment') {
            steps {
                sh 'python3 -m venv myenv'
                
                sh '. myenv/bin/activate'
            }
        }

        stage('Install Dependencies') {
            steps {
                sh 'ls -al'

                sh 'myenv/bin/pip install -r requirements.txt'
            }
        }

        stage('Delete existing Docker images and containers') {
            steps {
                sh '''
                    if docker container inspect fastapi_server >/dev/null 2>&1; then
                        echo "container exists locally"
                        docker stop fastapi_server
                        docker rm fastapi_server
                    else
                        echo "container does not exist locally"
                    fi
                    if docker image inspect fastapi_image >/dev/null 2>&1; then
                        echo "Image exists locally"
                        docker rmi fastapi_image
                    else
                        echo "Image does not exist locally"
                    fi
                '''
            }
        }

        stage('Build and Deploy Docker') {
            steps {
                sh '''
                    echo [REC] Build Docker Image!
                    docker build -t fastapi_image -f Dockerfile .
                    echo [REC] Run Docker Container!
                    docker run -dp 8000:8000 --name fastapi_server fastapi_image
                '''
            }
        }
    }

    post {
        success {
            script {
                def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                def GIT_COMMIT_MSG = sh(script: 'git log -1 --pretty=%B ${GIT_COMMIT}', returnStdout: true).trim()
                mattermostSend(color: 'good', message: "✅ Build & Deployment succeeded: ${env.JOB_NAME} (<${env.BUILD_URL}|#${env.BUILD_NUMBER}>)\nBranch: rec/develop\nCommit message: ${GIT_COMMIT_MSG} by ${Author_ID}(${Author_Name})\n")
            }
        }
        failure {
            script {
                def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                def GIT_COMMIT_MSG = sh(script: 'git log -1 --pretty=%B ${GIT_COMMIT}', returnStdout: true).trim()
                mattermostSend(color: 'danger', message: "❌ Build & Deployment failed: ${env.JOB_NAME} (<${env.BUILD_URL}|#${env.BUILD_NUMBER}>)\nBranch: rec/develop\nCommit message: ${GIT_COMMIT_MSG} by ${Author_ID}(${Author_Name})\n")
            }
        }
    }
}
