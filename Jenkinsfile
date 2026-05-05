pipeline {
    agent any

    environment {
        MAVEN_HOME = "C:\\Program Files\\Apache\\Maven"
        PATH = "${env.PATH};${env.MAVEN_HOME}\\bin"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Detect Branch') {
            steps {
                script {
                    echo "Current branch: ${env.BRANCH_NAME}"

                    if (env.BRANCH_NAME == 'main') {
                        echo " Production (main branch)"
                    } 
                    else if (env.BRANCH_NAME == 'develop') {
                        echo " Integration (develop branch)"
                    } 
                    else if (env.BRANCH_NAME.startsWith('feature/')) {
                        echo "Feature branch"
                    }
                }
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage('Static Code Analysis') {
            steps {
                // если у тебя подключен checkstyle в pom.xml
                bat 'mvn checkstyle:check'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Package') {
            steps {
                bat 'mvn package'
            }
        }

    }

    post {

        success {
            echo 'Pipeline SUCCESS'

            // сохраняем собранный jar в Jenkins
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }

        failure {
            echo ' Pipeline FAILED'
        }
    }
}
