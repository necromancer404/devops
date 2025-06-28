pipeline {
    agent any

    environment {
        MAVEN_HOME = tool 'Maven'
        PATH = "${MAVEN_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                git credentialsId: 'github-https-pat', url: 'https://github.com/necromancer404/devops.git'
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean package'
                // Commented out until JUnit plugin is installed:
                // junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('Archive Artifacts and Reports') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }
}
