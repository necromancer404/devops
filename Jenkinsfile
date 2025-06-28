pipeline {
    agent {
        dockerContainer {
            image 'maven:3.9.6-eclipse-temurin-21'
            args '-v /root/.m2:/root/.m2' // optional: cache Maven dependencies
        }
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/necromancer404/devops.git'
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
