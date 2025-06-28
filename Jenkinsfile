pipeline {
    agent any

    tools {
        maven 'Maven'   // Ensure this matches the name in Jenkins -> Global Tool Configuration
        jdk 'default'   // Or your JDK name
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'file:///home/hamza/eclipse-workspace/encrypt'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                junit 'target/surefire-reports/*.xml'
            }
        }
    }
}
